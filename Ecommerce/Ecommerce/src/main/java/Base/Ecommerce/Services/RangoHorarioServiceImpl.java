package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.RangoHorario;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.RangoHorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RangoHorarioServiceImpl extends BaseServiceImpl<RangoHorario,Long> implements RangoHorarioService{
    @Autowired
    RangoHorarioRepository rangoHorarioRepository;
    public RangoHorarioServiceImpl(BaseRepository<RangoHorario, Long> baseRepository, RangoHorarioRepository rangoHorarioRepository) {
        super(baseRepository);
    }
}
