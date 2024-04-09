package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.RangoHorario;
import Base.Ecommerce.Services.RangoHorarioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/rangoHorario")
public class RangoHorarioController extends BaseControllerImpl<RangoHorario, RangoHorarioServiceImpl> {
}
