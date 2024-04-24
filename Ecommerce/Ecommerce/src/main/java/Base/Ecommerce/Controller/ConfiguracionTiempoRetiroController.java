package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;
import Base.Ecommerce.Services.ConfiguracionTiempoRetiroServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/configTiempoRetiro")
public class ConfiguracionTiempoRetiroController extends BaseControllerImpl<ConfiguracionTiempoRetiro, ConfiguracionTiempoRetiroServiceImpl>{

    //@GetMapping
    //public ResponseEntity<?> findTiempoActual()
}
