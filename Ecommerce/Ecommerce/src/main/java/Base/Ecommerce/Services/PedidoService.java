package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Pedido;

import java.util.List;

public interface PedidoService extends BaseService<Pedido,Long>{
    //Buscar los pedidos de un cliente especifico
    List<Pedido> getPedidosCliente(Long idCliente) throws Exception;
}
