package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.FormaPago;
import Base.Ecommerce.Services.FormaPagoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "FormaPago")
public class FormaPagoController extends BaseControllerImpl<FormaPago, FormaPagoServiceImpl>{
}
