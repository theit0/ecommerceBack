package Base.Ecommerce.ControllerPay;


import Base.Ecommerce.Controller.BaseControllerImpl;
import Base.Ecommerce.Entity.*;
import Base.Ecommerce.Repositories.ClienteRepository;
import Base.Ecommerce.Repositories.PedidoRepository;
import Base.Ecommerce.Repositories.ProductoRepository;
import Base.Ecommerce.ServicePay.MercadoPagoServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping(path = "api/mp")
public class MercadoPagoController extends BaseControllerImpl<MP, MercadoPagoServiceImpl> {

    @Value("${MP_ACCESS_TOKEN}") // Inyecta el valor de la variable de entorno MP_ACCESS_TOKEN
    private String accessToken;


    @PostMapping("/webhook")
    public ResponseEntity<?> handleNotification(@RequestBody String payload,@RequestParam Long Clienteid) {
       try {

           servicio.handleNotification(payload,Clienteid);
           return ResponseEntity.status(HttpStatus.OK).body("PedidoCreado");
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\":\"" +e.getMessage() +"\"}"));
       }
    }

    @PostMapping("/preference")
    public ResponseEntity<?> getList (@RequestBody Pedido pedido,@RequestParam Long clienteid) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(servicio.getList(pedido,clienteid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\":\"" +e.getMessage() +"\"}"));

        }


    }
}
