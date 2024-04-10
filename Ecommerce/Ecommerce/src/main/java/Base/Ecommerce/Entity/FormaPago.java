package Base.Ecommerce.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "formapago")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormaPago extends Base{
    private LocalDate fechaBaja;
    private String nombre;
}
