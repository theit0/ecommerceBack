package Base.Ecommerce.Controller;

import Base.Ecommerce.DTO.UserDTO;
import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Repositories.ClienteRepository;
import Base.Ecommerce.Services.ClienteService;
import Base.Ecommerce.Services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cliente")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteServiceImpl>{
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.saveUser(userDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"error\":\"Error porfavor intente mas tarde. \"}"
            );
        }
    }


    @PostMapping("/findUser")
    public ResponseEntity<?>  findUser(@RequestBody UserDTO userDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findUser(userDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"error\":\"Error porfavor intente mas tarde. \"}"
            );
        }
    }


    @PostMapping("/findUserByAuth0ID")
    public ResponseEntity<?> findUserByAuth0ID(@RequestBody UserDTO userDTO) {
        try {
            Cliente cliente = clienteRepository.findByAuth0id(userDTO.getUserID());
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"error\":\"Error porfavor intente mas tarde. \"}"
            );
        }
    }
}
