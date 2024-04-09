package Base.Ecommerce.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
@Entity
@Table(name = "Rangohorario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RangoHorario extends Base{
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private Integer tiempoDemora;
}
