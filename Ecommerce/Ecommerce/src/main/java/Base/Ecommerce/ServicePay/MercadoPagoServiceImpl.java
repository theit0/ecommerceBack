package Base.Ecommerce.ServicePay;

import Base.Ecommerce.Entity.*;
import Base.Ecommerce.Repositories.*;
import Base.Ecommerce.ServicePay.MercadoPagoService;
import Base.Ecommerce.Services.BaseServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service

public class MercadoPagoServiceImpl extends BaseServiceImpl<MP,Long>implements MercadoPagoService {
    @Value("${MP_ACCESS_TOKEN}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String accessToken;
    @Value("${DIRECCION_GMAIL}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String direccionGmail;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository;
    @Autowired
    private JavaMailSender mailSender;

    public MercadoPagoServiceImpl(myBaseRepository baseRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository) {
        super(baseRepository);
    }
    //Metodo q genera la preferencia de pago
    @Override
    public String getList(@RequestBody Pedido pedido, @RequestParam Long Clienteid) throws Exception {

        try {

            MercadoPagoConfig.setAccessToken(accessToken);

            List<DetallePedido> detalles = pedido.getDetallesPedido();
            List<PreferenceItemRequest> items = new ArrayList<>();
            for (DetallePedido detalle : detalles) {
                Long id = detalle.getProducto().getId();
                int quantity = detalle.getQuantity();
                String title = detalle.getProducto().getTitle();
                float price = detalle.getProducto().getPrice();
                //Preferencia de venta
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(id.toString())
                        .title(title)
                        .quantity(quantity)
                        .unitPrice(new BigDecimal(price))
                        .currencyId("ARS")
                        .build();
                items.add(itemRequest);
            }


            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
                    .builder()
                    .success("https://nike.com.ar")
                    .pending("https://youtube.com")
                    .failure("https://youtube.com")
                    .build();
            PreferenceRequest preferenceRequest = PreferenceRequest
                    .builder()
                    .items(items)
                    .notificationUrl("https://22c3-191-82-13-64.ngrok-free.app/api/mp/webhook?Clienteid=" + Clienteid) //cambiarlo
                    .backUrls(backUrls)
                    .build();
            PreferenceClient client = new PreferenceClient();

            Preference preference = client.create(preferenceRequest);

            return preference.getId();
        } catch (MPException | MPApiException e) {
            System.out.println(e.getMessage()); // Or use a logger
            return e.toString();
        }

    }
    @Override
    public void handleNotification(@RequestBody String payload,@RequestParam Long Clienteid) throws Exception{
        try {

            // String JSON
            String jsonString = payload;

            // Crear un objeto Gson
            Gson gson = new Gson();

            // Parsear el string JSON a un mapa de cadenas
            Map<String, Map<String, String>> jsonMap = gson.fromJson(jsonString, Map.class);
            Map<String, String> data = jsonMap.get("data");
            String paymentId = data.get("id");
            //System.out.println(paymentId);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.mercadopago.com/v1/payments/"+ paymentId))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();
            try {
                //http client sirve para enviar la solicitud HTTP
                HttpClient httpClient = HttpClient.newHttpClient();
                // Se envía la solicitud HTTP de forma asíncrona utilizando el método sendAsync() de HttpClient. Esto devuelve un CompletableFuture que representa una operación asincrónica que eventualmente producirá una respuesta HTTP.
                CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response = future.get();
                if (response.statusCode() == 200) {
                    //System.out.println(response.body());
                    createPedido(response, Clienteid);

                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            System.out.println(e);
        }

    }
    public void createPedido(HttpResponse<String> response, Long Clienteid) throws Exception{

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
        System.out.println(cliente.getNombre());
        // Crear un nuevo pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDateTime.now()); // O establece la fecha que desees
        pedido.setMontoTotal(rootNode.path("transaction_amount").asText()); // O ajusta según tu lógica

        // Asignar los detalles del pedido
        pedido.setDetallesPedido(detallesPedido);
        //Buscar cual es la demora actual
        //Obtengo la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Obtener el día de la semana actual como un objeto DayOfWeek
        DayOfWeek diaSemana = fechaActual.getDayOfWeek();

        // Obtener el nombre completo del día de la semana actual en español
        String nombreDia = diaSemana.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        //Busco en la BD la clase configuracion retiro que este en vigencia y para el dia actual
        ConfiguracionTiempoRetiro configActual = configuracionTiempoRetiroRepository.findActual(fechaActual, nombreDia);
        List<RangoHorario> rangos = configActual.getRangoHorario();
        //recorro todos los rangos horarios configurados y selecciono el de horario actual y seteo la demora al pedido
        for (RangoHorario rango : rangos) {
            LocalTime horaActual = LocalTime.now();
            LocalTime horaDesde = rango.getHoraDesde();
            LocalTime horaHasta = rango.getHoraHasta();
            if(horaActual.isAfter(horaDesde) && horaActual.isBefore(horaHasta)){
                Integer demora = rango.getTiempoDemora();
                pedido.setDemora(demora);
                break;
            }


        }
        if (pedido.getDemora() == null) {
            pedido.setDemora(60);
        }
        // Aquí puedes guardar el pedido en tu base de datos o realizar otras acciones según tu lógica de negocio
        pedidoRepository.save(pedido);
        //Metodo que manda el mail de notificacion al cliente
        sendEmail(pedido,cliente);

    }
    public void sendEmail (Pedido pedido, Cliente cliente){
        SimpleMailMessage mailMessage = new SimpleMailMessage();


        try {
            //obtengo la informacion del pedido
            Long nroPedido = pedido.getId();
            String receptor = cliente.getEmail();
            LocalDateTime fecha = pedido.getFecha();
            List<DetallePedido> detalles = pedido.getDetallesPedido();
            String montoTotal = pedido.getMontoTotal();

            //Construir el contenido del correo
            StringBuilder messageBody = new StringBuilder();
            messageBody.append("¡Pedido confirmado!\n\n");
            messageBody.append("Hola ").append(cliente.getNombre()).append(", tu pedido ya fue confirmado y está próximo a prepararse.\n\n");
            messageBody.append("Fecha: ").append(fecha).append("\n");
            messageBody.append("Pedido: ").append(nroPedido).append("\n");
            messageBody.append("Tiempo estimado: 30 minutos\n\n");
            messageBody.append("Detalles del pedido:\n");

            for (DetallePedido detalle : detalles) {
                Producto producto = detalle.getProducto();
                int cantidad = detalle.getQuantity();
                double subtotal = detalle.getSubtotalPedido();

                // Agregar detalle al cuerpo del correo electrónico
                messageBody.append("(").append(cantidad).append(") ").append(producto.getTitle()).append("    "+ subtotal +"$").append("\n");
            }
            messageBody.append("\nTotal: $").append(montoTotal).append("\n\n");
            messageBody.append("Para más información puedes llamar al 2617000018 o escribir a zandy burguer\n\n");
            messageBody.append("¡Gracias por tu compra!");
            //informacion del email
            mailMessage.setFrom(direccionGmail);
            mailMessage.setTo(receptor);
            mailMessage.setSubject("¡Pedido Confirmado!");
            mailMessage.setText(messageBody.toString());
            mailSender.send(mailMessage);
            System.out.println("el mail fue enviado con exito");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }
}
