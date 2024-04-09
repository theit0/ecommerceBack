package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.ConfiguracionTiempoRetiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionTiempoRetiroServiceImpl extends BaseServiceImpl<ConfiguracionTiempoRetiro,Long> implements ConfiguracionTiempoRetiroService{

   @Autowired
   ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository;

    public ConfiguracionTiempoRetiroServiceImpl(BaseRepository<ConfiguracionTiempoRetiro, Long> baseRepository, ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository) {
        super(baseRepository);
    }
}
