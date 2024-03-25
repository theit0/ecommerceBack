package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Services.PedidoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "Pedido")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl>{
}
