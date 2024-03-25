package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Producto;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto,Long> implements ProductoService{
    @Autowired
    ProductoRepository productoRepository;

    public ProductoServiceImpl(BaseRepository<Producto, Long> baseRepository, ProductoRepository productoRepository) {
        super(baseRepository);


    }
}