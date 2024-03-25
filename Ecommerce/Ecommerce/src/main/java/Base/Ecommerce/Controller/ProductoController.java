package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Producto;
import Base.Ecommerce.Services.ProductoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl>{


}
