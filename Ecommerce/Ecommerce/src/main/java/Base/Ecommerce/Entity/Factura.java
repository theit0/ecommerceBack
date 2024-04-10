package Base.Ecommerce.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factura")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Factura extends Base{

    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    private LocalDate fechaFacturacion;

    private String mp_payment_id;

    private Long mpMerchantOrderId;

    private String mpPaymentType;

    private String PreferenceId;

    private String totalVenta;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleFactura> detallesFactura = new ArrayList<DetalleFactura>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_formaPago")
    private FormaPago formaPago;

}
