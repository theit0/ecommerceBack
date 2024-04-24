package Base.Ecommerce.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Dia")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dia extends Base{
    private String nombre;
}
