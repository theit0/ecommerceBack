package Base.Ecommerce.Controller;

import Base.Ecommerce.DTO.UserDTO;
import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Repositories.ClienteRepository;
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
        Cliente cliente = new Cliente();
        cliente.setAuth0id(userDTO.getUserID());
        cliente.setEmail(userDTO.getEmail());
        clienteRepository.save(cliente);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/findUser")
    public ResponseEntity<?>  findUser(@RequestBody UserDTO userDTO){
        try {
            String auth0id = userDTO.getUserID();
            Cliente cliente = clienteRepository.findByAuth0id(auth0id);
            // System.out.println(cliente.getAuth0id());
            if(cliente == null) {
                Cliente nuevoCliente = new Cliente();
                String email = userDTO.getEmail();
                nuevoCliente.setAuth0id(auth0id);
                nuevoCliente.setEmail(email);
                clienteRepository.save(nuevoCliente);
                return ResponseEntity.status(HttpStatus.OK).body("se guardo en la bd");
            }else {
                return ResponseEntity.status(HttpStatus.OK).body("ya estaba registrado en la bd");
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\":\"" +e.getMessage() +"\"}"));
        }


    }
}
