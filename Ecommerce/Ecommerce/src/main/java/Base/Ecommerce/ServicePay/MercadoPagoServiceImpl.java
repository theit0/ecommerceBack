package Base.Ecommerce.ServicePay;

import Base.Ecommerce.Entity.*;
import Base.Ecommerce.Repositories.*;
import Base.Ecommerce.Services.BaseServiceImpl;
import Base.Ecommerce.Services.FacturaService;
import Base.Ecommerce.Services.PedidoService;
import com.google.gson.Gson;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service

public class MercadoPagoServiceImpl extends BaseServiceImpl<MP,Long>implements MercadoPagoService {
    @Value("${MP_ACCESS_TOKEN}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String accessToken;
    @Autowired
    PedidoService pedidoService;
    @Autowired
    FacturaService facturaService;

    public MercadoPagoServiceImpl(BaseRepository<MP,Long> baseRepository,MPRepository mpRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository, FacturaRepository facturaRepository) {
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
                    .notificationUrl("https://ccd7-191-82-25-251.ngrok-free.app/api/mp/webhook?Clienteid=" + Clienteid) //cambiarlo
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
                    Pedido pedido = pedidoService.createPedido(response, Clienteid);
                    facturaService.createFacturacion(pedido,payload);

                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }





}
