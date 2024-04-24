package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.PedidoRepository;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl extends BaseServiceImpl<Pedido,Long> implements PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    public PedidoServiceImpl(BaseRepository<Pedido, Long> baseRepository, PedidoRepository pedidoServiceRepository) {
        super(baseRepository);
    }
    //Buscar los pedidos de un cliente especifico
    @Override
    public List<Pedido> getPedidosCliente(Long idCliente) throws Exception {
        try {
            List<Pedido> pedidos = pedidoRepository.getPedidosCliente(idCliente);
            return pedidos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}