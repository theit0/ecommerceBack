package Base.Ecommerce.Repositories;

import Base.Ecommerce.Entity.Base;
import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;
import Base.Ecommerce.Entity.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long> {
    //Buscar los pedidos de un cliente especifico
    @Query(
            value = "SELECT * FROM pedido WHERE id_cliente = :idCliente",
            nativeQuery = true
    )
    List<Pedido> getPedidosCliente(Long idCliente);
}


