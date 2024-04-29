package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;
import Base.Ecommerce.Services.ConfiguracionTiempoRetiroServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/configTiempoRetiro")
public class ConfiguracionTiempoRetiroController extends BaseControllerImpl<ConfiguracionTiempoRetiro, ConfiguracionTiempoRetiroServiceImpl> {

    //@GetMapping
    //public ResponseEntity<?> findTiempoActual()
    //Crear nuevo configTiempoRetiro
    @PostMapping("/newConfig")
    public ResponseEntity<?> newConfig(@RequestBody ConfiguracionTiempoRetiro config) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.newConfig(config));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"error\":\"Error porfavor intente mas tarde. \"}"
            );
        }
    }
    //Actualizar configTiemporetiro
}
