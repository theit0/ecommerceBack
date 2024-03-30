package Base.Ecommerce.Repositories;


import Base.Ecommerce.Entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente,Long> {
    Cliente findByAuth0id(String auth0Id);
}