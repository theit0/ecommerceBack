package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.FormaPago;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.FormaPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormaPagoServiceImpl extends BaseServiceImpl<FormaPago,Long> implements FormaPagoService {
    @Autowired
    FormaPagoRepository formaPagoRepository;

    public FormaPagoServiceImpl(BaseRepository<FormaPago, Long> baseRepository, FormaPagoRepository formaPagoRepository) {
        super(baseRepository);
        this.formaPagoRepository = formaPagoRepository;
    }
}
