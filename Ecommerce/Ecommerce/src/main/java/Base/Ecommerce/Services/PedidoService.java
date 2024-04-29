package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Pedido;

import java.net.http.HttpResponse;
import java.util.List;

public interface PedidoService extends BaseService<Pedido,Long>{
    //Buscar los pedidos de un cliente especifico
    List<Pedido> getPedidosCliente(Long idCliente) throws Exception;
    //Crear pedido despues que se confirme el pago
    public Pedido createPedido(HttpResponse<String> response, Long Clienteid) throws Exception;

}
