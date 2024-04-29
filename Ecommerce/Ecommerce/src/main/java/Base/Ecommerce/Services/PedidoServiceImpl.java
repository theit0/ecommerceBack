package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.*;
import Base.Ecommerce.Repositories.*;
import Base.Ecommerce.ServiceEmail.GmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.sql.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PedidoServiceImpl extends BaseServiceImpl<Pedido,Long> implements PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository;
    @Autowired
    GmailService gmailService;

    public PedidoServiceImpl(BaseRepository<Pedido, Long> baseRepository, PedidoRepository pedidoServiceRepository) {
        super(baseRepository);
    }

    //Buscar los pedidos de un cliente especifico
    @Override
    public List<Pedido> getPedidosCliente(Long idCliente) throws Exception {
        try {
            List<Pedido> pedidos = pedidoRepository.getPedidosCliente(idCliente);
            return pedidos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido createPedido(HttpResponse<String> response, Long Clienteid) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        // Obtener la información de los items
        JsonNode itemsNode = rootNode.path("additional_info").path("items");
        List<DetallePedido> detallesPedido = new ArrayList<>();
        for (JsonNode itemNode : itemsNode) {

            String id = itemNode.path("id").asText();
            String title = itemNode.path("title").asText();
            int quantity = itemNode.path("quantity").asInt();
            BigDecimal unitPrice = new BigDecimal(itemNode.path("unit_price").asText());
            Optional<Producto> optionalProducto = productoRepository.findById(Long.parseLong(id));


            if (optionalProducto.isPresent()) {

                Producto producto = optionalProducto.get();
                DetallePedido detalle = new DetallePedido();
                detalle.setQuantity(quantity);

                detalle.setSubtotalPedido(unitPrice.multiply(BigDecimal.valueOf(quantity)).intValue());
                detalle.setProducto(producto);
                detallesPedido.add(detalle);

            } else {
                throw new Exception();

            }
        }
        //Buscar el Cliente de la sesion
        Optional<Cliente> optionalCliente = clienteRepository.findById(Clienteid);
        Cliente cliente = optionalCliente.get();

        // Crear un nuevo pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDateTime.now()); // O establece la fecha que desees
        pedido.setMontoTotal(rootNode.path("transaction_amount").asText()); // O ajusta según tu lógica

        // Asignar los detalles del pedido
        pedido.setDetallesPedido(detallesPedido);


        Integer demora = calcularDemora();

        pedido.setDemora(demora);
        // Aquí puedes guardar el pedido en tu base de datos o realizar otras acciones según tu lógica de negocio
        pedidoRepository.save(pedido);
        //Metodo que manda el mail de notificacion al cliente
        gmailService.sendEmail(pedido, cliente);
        return pedido;

    }

    public Integer calcularDemora() {
        //Obtengo la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Obtener el día de la semana actual como un objeto DayOfWeek
        DayOfWeek diaSemana = fechaActual.getDayOfWeek();

        // Obtener el nombre completo del día de la semana actual en español
        String nombreDia = diaSemana.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));

        ConfiguracionTiempoRetiro configActual = configuracionTiempoRetiroRepository.findActual(fechaActual, nombreDia);
        if(configActual == null){
            //Retorna 60 si no hay ninguna config para ese dia
            return 60;
        }
        List<RangoHorario> rangos = configActual.getRangoHorario();
        //recorro todos los rangos horarios configurados y selecciono el de horario actual y seteo la demora al pedido
        for (RangoHorario rango : rangos) {

            LocalTime horaActual = LocalTime.now();
            LocalTime horaDesde = rango.getHoraDesde();
            LocalTime horaHasta = rango.getHoraHasta();

            if (horaActual.isAfter(horaDesde) && horaActual.isBefore(horaHasta)) {
                Integer demora = rango.getTiempoDemora();
                return demora;
            }


        }// Si no se encuentra un rango horario válido, se devuelve un valor predeterminado (60 en este caso)
        return 60;
    }
}
