package Base.Ecommerce.Services;

import Base.Ecommerce.DTO.ProductoDTO;
import Base.Ecommerce.Entity.Producto;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto,Long> implements ProductoService{

    @Autowired
    ProductoRepository productoRepository;

    public ProductoServiceImpl(BaseRepository<Producto, Long> baseRepository, ProductoRepository productoRepository) {
        super(baseRepository);


    }

    @Override
    public List<Producto> filtradoCategoria(String nombreCat) throws Exception {
        try {
            List<Producto> productos = productoRepository.filtradoCategoria(nombreCat);
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public List<ProductoDTO> productosMasVendidos() throws Exception {
        try {
            List<ProductoDTO> productoDTOS = productoRepository.productosMasVendidos();
            return productoDTOS;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}