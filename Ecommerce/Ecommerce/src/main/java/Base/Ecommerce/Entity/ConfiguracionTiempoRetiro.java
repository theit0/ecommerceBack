package Base.Ecommerce.Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ConfiguracionTiempoRetiro")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//esta entidad la usaremos para que el administrador pueda definier el segun la demanda del dia cual va a ser el tiempo de retiro
public class ConfiguracionTiempoRetiro extends Base {

    private LocalDate fechaDesde;

    private LocalDate fechaHasta;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dia")
    private Dia dia;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RangoHorario> rangoHorario = new ArrayList<RangoHorario>();




}
