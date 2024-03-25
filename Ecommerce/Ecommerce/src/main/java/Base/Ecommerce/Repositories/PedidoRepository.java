package Base.Ecommerce.Repositories;

import Base.Ecommerce.Entity.Base;
import Base.Ecommerce.Entity.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long> {
}
