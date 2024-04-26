package Base.Ecommerce.DTO;

import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SqlResultSetMapping(
        name = "productosMasVendidos",
        entities = {
                @EntityResult(
                        entityClass = ProductoDTO.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "title", column = "title"),
                                @FieldResult(name = "price", column = "price"),
                                @FieldResult(name = "url_imagen", column = "url_imagen"),
                                @FieldResult(name = "total_vendido", column = "total_vendido")
                        }
                )
        }
)
public interface ProductoDTO {
    Long getid();
    String gettitle();
    Float getprice();
    String geturl_imagen();
    Integer gettotal_vendido();
}