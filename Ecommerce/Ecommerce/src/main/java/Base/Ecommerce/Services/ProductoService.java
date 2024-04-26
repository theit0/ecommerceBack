package Base.Ecommerce.Services;

import Base.Ecommerce.DTO.ProductoDTO;
import Base.Ecommerce.Entity.Producto;

import java.util.List;

public interface ProductoService extends BaseService<Producto,Long>{
    //productos de una categoria
    List<Producto> filtradoCategoria(String nombreCat) throws Exception;
    //Top productos vendidos del mes
    List<ProductoDTO> productosMasVendidos() throws Exception;
}
