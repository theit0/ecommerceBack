package Base.Ecommerce.Services;

import Base.Ecommerce.DTO.UserDTO;
import Base.Ecommerce.Entity.Cliente;

public interface ClienteService extends BaseService<Cliente,Long>{
    String saveUser(UserDTO userDTO) throws Exception;

    String findUser(UserDTO userDTO) throws Exception;
}
