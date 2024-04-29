package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.DetalleFactura;
import Base.Ecommerce.Entity.DetallePedido;
import Base.Ecommerce.Entity.Factura;
import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.FacturaRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura,Long> implements FacturaService {
    @Autowired
    FacturaRepository facturaRepository;

    public FacturaServiceImpl(BaseRepository<Factura, Long> baseRepository, FacturaRepository facturaRepository) {
        super(baseRepository);
        this.facturaRepository = facturaRepository;
    }
    public void createFacturacion(Pedido pedido, String payload){

        Factura factura = new Factura();
        List<DetalleFactura> detalleFacturas = new ArrayList<>();
        factura.setPedido(pedido);
        List<DetallePedido> detallePedidos = pedido.getDetallesPedido();


        for (DetallePedido detallePedido : detallePedidos){

            DetalleFactura detalleFactura = new DetalleFactura();
            detalleFactura.setQuantity(detallePedido.getQuantity());
            detalleFactura.setSubtotalPedido(detallePedido.getSubtotalPedido());
            detalleFactura.setProducto(detallePedido.getProducto());
            detalleFacturas.add(detalleFactura);
        }

        factura.setDetallesFactura(detalleFacturas);
        factura.setFormaPago(pedido.getFormaPago());
        factura.setFechaAlta(LocalDate.now());
        factura.setFechaFacturacion(LocalDate.now());
        factura.setTotalVenta(pedido.getMontoTotal());

        //informacion del pago
        // String JSON
        String jsonString = payload;

        // Crear un objeto Gson
        Gson gson = new Gson();

        // Parsear el string JSON a un mapa de cadenas
        Map<String, Map<String, String>> jsonMap = gson.fromJson(jsonString, Map.class);
        Map<String, String> data = jsonMap.get("data");
        String paymentId = data.get("id");
        factura.setMp_payment_id(paymentId);

        facturaRepository.save(factura);
    }
}
