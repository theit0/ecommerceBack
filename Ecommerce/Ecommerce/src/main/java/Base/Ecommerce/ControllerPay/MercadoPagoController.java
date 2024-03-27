package Base.Ecommerce.ControllerPay;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;


import Base.Ecommerce.Entity.DetallePedido;
import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Entity.Producto;
import com.mercadopago.*;
import com.mercadopago.MercadoPagoConfig.*;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping(path = "api/mp")
public class MercadoPagoController {

    @Value("${MP_ACCESS_TOKEN}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String accessToken;

    @PostMapping("/preference")
    public String getList (@RequestBody Pedido pedido) {

        /*
        if (pedido == null) {
            return "error jsons :/";
        }
        */
        try{
            MercadoPagoConfig.setAccessToken(accessToken);

            List<DetallePedido> detalles = pedido.getDetallesPedido();
            List<PreferenceItemRequest> items = new ArrayList<>();
            for (DetallePedido detalle : detalles){
                int quantity = detalle.getQuantity();
                String title = detalle.getProducto().getTitle();
                int price = detalle.getSubtotalPedido();
                //Preferencia de venta
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
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
                    .backUrls(backUrls)
                    .build();
            PreferenceClient client = new PreferenceClient();



            Preference preference = client.create(preferenceRequest);

            return preference.getId();
        }catch (MPException | MPApiException e){
            System.out.println(e.getMessage()); // Or use a logger
            return e.toString();
        }
    }
}
