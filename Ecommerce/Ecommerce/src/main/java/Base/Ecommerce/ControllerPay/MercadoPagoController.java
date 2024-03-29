package Base.Ecommerce.ControllerPay;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;


import Base.Ecommerce.Entity.DetallePedido;
import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Entity.Producto;
import Base.Ecommerce.Repositories.PedidoRepository;
import Base.Ecommerce.Repositories.ProductoRepository;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController

@RequestMapping(path = "api/mp")
public class MercadoPagoController {

    @Value("${MP_ACCESS_TOKEN}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String accessToken;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public MercadoPagoController(PedidoRepository pedidoRepository,
                                 ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

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

                    // Crear un nuevo pedido
                    Pedido pedido = new Pedido();
                    pedido.setFecha(LocalDateTime.now()); // O establece la fecha que desees
                    pedido.setMontoTotal(rootNode.path("transaction_amount").asText()); // O ajusta según tu lógica

                    // Asignar los detalles del pedido
                    pedido.setDetallesPedido(detallesPedido);

                    // Aquí puedes guardar el pedido en tu base de datos o realizar otras acciones según tu lógica de negocio
                    pedidoRepository.save(pedido);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
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
                    .notificationUrl("https://6caa-190-15-220-246.ngrok-free.app/api/mp/webhook") //cambiarlo
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
