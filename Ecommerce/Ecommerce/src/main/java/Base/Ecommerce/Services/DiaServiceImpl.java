package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Dia;
import Base.Ecommerce.Entity.Factura;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.DiaRepository;
import Base.Ecommerce.Repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaServiceImpl extends BaseServiceImpl<Dia,Long> implements DiaService{
    @Autowired
    DiaRepository diaRepository;

    public DiaServiceImpl(BaseRepository<Dia, Long> baseRepository, DiaRepository diaRepository) {
        super(baseRepository);
        this.diaRepository = diaRepository;
    }
}
