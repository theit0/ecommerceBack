package Base.Ecommerce.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin",produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    @GetMapping(value = "")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Este es un endpoint de administrador. Podes ver esta respuesta porque tu usuario tiene el rol 'adminstrador'\"}");
    }

}