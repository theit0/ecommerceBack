package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;

public interface ConfiguracionTiempoRetiroService extends BaseService<ConfiguracionTiempoRetiro,Long>{
    public ConfiguracionTiempoRetiro newConfig(ConfiguracionTiempoRetiro config) throws Exception;
}
