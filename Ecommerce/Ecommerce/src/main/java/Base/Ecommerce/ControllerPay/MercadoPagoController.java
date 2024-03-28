package Base.Ecommerce.ControllerPay;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;


import Base.Ecommerce.Entity.DetallePedido;
import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Entity.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mercadopago.*;
import com.mercadopago.MercadoPagoConfig.*;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping(path = "api/mp")
public class MercadoPagoController {

    @Value("${MP_ACCESS_TOKEN}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String accessToken;

    @PostMapping("/webhook")
    public void handleNotification(@RequestBody String payload) {
        try {
            // String JSON
            String jsonString = payload;

            // Crear un objeto Gson
            Gson gson = new Gson();

            // Parsear el string JSON a un mapa de cadenas
            Map<String, Map<String, String>> jsonMap = gson.fromJson(jsonString, Map.class);
            Map<String, String> data = jsonMap.get("data");
            String id = data.get("id");
            System.out.println(id);
        }catch (Exception e) {
            System.out.println(e);
        }

    }

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
                    .notificationUrl("https://78cf-190-15-220-246.ngrok-free.app/api/mp/webhook") //cambiarlo
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
