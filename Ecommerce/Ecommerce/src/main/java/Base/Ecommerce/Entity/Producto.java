package Base.Ecommerce.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "producto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Producto extends Base {

    private String title;
    private String descripcion;
    private float price;
    private String urlImagen;

    @Column(name = "fechaBaja")
    private LocalDateTime fechaBaja;

}
