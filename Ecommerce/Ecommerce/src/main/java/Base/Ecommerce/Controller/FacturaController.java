package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Factura;
import Base.Ecommerce.Services.FacturaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "Factura")
public class FacturaController extends BaseControllerImpl<Factura, FacturaServiceImpl>{
}
