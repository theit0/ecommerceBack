package Base.Ecommerce.ServicePay;

import Base.Ecommerce.Entity.MP;
import Base.Ecommerce.Entity.Pedido;
import Base.Ecommerce.Services.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface MercadoPagoService extends BaseService<MP,Long> {
    String getList(@RequestBody Pedido pedido, @RequestParam Long Clienteid) throws Exception;
    void handleNotification(@RequestBody String payload,@RequestParam Long Clienteid) throws Exception;
}
