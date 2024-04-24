package Base.Ecommerce.Entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class EstadoPedido extends Base{

    private String nombre;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private String colorSignificativo;


}
