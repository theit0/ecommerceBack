package Base.Ecommerce.ServiceEmail;

import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Entity.Pedido;

public interface GmailService {
    public void sendEmail (Pedido pedido, Cliente cliente) throws Exception;
}
