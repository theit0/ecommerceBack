package Base.Ecommerce.Repositories;

import Base.Ecommerce.DTO.ProductoDTO;
import Base.Ecommerce.Entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto,Long>{
    //todos los productos de una categoria
    @Query(
            value = "SELECT p.* FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id "+
                    "WHERE p.fecha_baja IS NULL AND c.nombre LIKE %:nombreCat%",
            nativeQuery = true
    )
    List<Producto> filtradoCategoria (String nombreCat);
    //productos mas vendidos
    @Query(
            value = "SELECT p.id, p.title, p.price, p.url_imagen, SUM(dp.quantity) AS total_vendido " +
                    "FROM detallepedido dp " +
                    "INNER JOIN producto p ON dp.fk_Producto = p.id " +
                    "INNER JOIN pedido_detalles_pedido pdp ON dp.id = pdp.detalles_pedido_id "+
                    "INNER JOIN pedido ped ON pdp.pedido_id = ped.id "+
                    "WHERE MONTH(ped.fecha) = MONTH(CURRENT_DATE()) AND YEAR(ped.fecha) = YEAR(CURRENT_DATE()) " +
                    "GROUP BY dp.fk_producto " +
                    "ORDER BY total_vendido DESC " +
                    "LIMIT 5;",
            nativeQuery = true
    )
    List<ProductoDTO> productosMasVendidos();

}
