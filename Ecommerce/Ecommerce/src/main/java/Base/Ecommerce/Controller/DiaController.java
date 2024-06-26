package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Dia;
import Base.Ecommerce.Services.DiaServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "Dia")
public class DiaController extends BaseControllerImpl<Dia, DiaServiceImpl>{
}
