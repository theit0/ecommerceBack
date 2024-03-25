package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Entity.DetallePedido;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.ClienteRepository;
import Base.Ecommerce.Repositories.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServiceImpl extends BaseServiceImpl<DetallePedido,Long> implements DetallePedidoService{
    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoServiceImpl(BaseRepository<DetallePedido, Long> baseRepository, DetallePedidoRepository detallePedidoRepository) {
        super(baseRepository);

    }
}
