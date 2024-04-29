package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Factura;
import Base.Ecommerce.Entity.Pedido;

public interface FacturaService extends BaseService<Factura,Long>{
    public void createFacturacion(Pedido pedido, String payload) throws Exception;
}
