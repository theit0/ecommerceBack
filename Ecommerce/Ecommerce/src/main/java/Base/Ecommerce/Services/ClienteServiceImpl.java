package Base.Ecommerce.Services;

import Base.Ecommerce.DTO.UserDTO;
import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente,Long> implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public ClienteServiceImpl(BaseRepository<Cliente, Long> baseRepository, ClienteRepository clienteRepository) {
        super(baseRepository);

    }

    @Override
    public String saveUser(UserDTO userDTO) throws Exception {
        try {
            Cliente cliente = new Cliente();
            cliente.setAuth0id(userDTO.getUserID());
            cliente.setEmail(userDTO.getEmail());
            clienteRepository.save(cliente);
            return "se guardo correctamente";
        }catch (Exception e){
            return "{\"error\":\"" +e.getMessage() +"\"}";
        }
    }

    @Override
    public String findUser(UserDTO userDTO) throws Exception {
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
                return "se guardo en la bd";
            }else {
                return "ya estaba registrado en la bd";
            }
        }
        catch (Exception e){
            return  "{\"error\":\"" +e.getMessage() +"\"}";
        }

    }
}