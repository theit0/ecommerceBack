package Base.Ecommerce.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente extends Base{
    private String nombre;
    @Column(name = "fechaAlta")
    private LocalDateTime fechaAlta;

    @Column(name = "fechaBaja")
    private LocalDateTime fechaBaja;

    @Column(name = "fechaModificacion")
    private LocalDateTime fechaModificacion;

    @NotNull
    private String auth0id;

    private String email;
}
