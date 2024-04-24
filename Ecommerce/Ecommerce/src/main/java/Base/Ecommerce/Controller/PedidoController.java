package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Services.PedidoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "Pedido")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl>{
    //busqueda de todos los pedidos de un usuario
    @PostMapping("/busquedaHistorial")
    public ResponseEntity<?> getHistorialCliente(@RequestBody Map<String, String> json) {
        try {
            Long idCliente = Long.parseLong(json.get("idCliente"));
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getPedidosCliente(idCliente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\":\"" + e.getMessage() + "\"}"));

        }
    }
}
