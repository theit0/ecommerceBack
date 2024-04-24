package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;
import Base.Ecommerce.Entity.Dia;
import Base.Ecommerce.Entity.RangoHorario;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.ConfiguracionTiempoRetiroRepository;
import Base.Ecommerce.Repositories.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracionTiempoRetiroServiceImpl extends BaseServiceImpl<ConfiguracionTiempoRetiro,Long> implements ConfiguracionTiempoRetiroService {

    @Autowired
    ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository;

    //metodo para chequear superposicion de horarios
    public ConfiguracionTiempoRetiroServiceImpl(BaseRepository<ConfiguracionTiempoRetiro, Long> baseRepository, ConfiguracionTiempoRetiroRepository configuracionTiempoRetiroRepository) {
        super(baseRepository);
    }

    //metodo para comparar superposicion de horarios
    private boolean seSuperponen(RangoHorario rango1, RangoHorario rango2) {
        // Caso 1: Rango2 comienza durante el período de Rango1
        boolean caso1 = rango1.getHoraDesde().isBefore(rango2.getHoraDesde()) && rango2.getHoraDesde().isBefore(rango1.getHoraHasta());

        // Caso 2: Rango2 termina durante el período de Rango1
        boolean caso2 = rango1.getHoraDesde().isBefore(rango2.getHoraHasta()) && rango2.getHoraHasta().isBefore(rango1.getHoraHasta());

        // Caso 3: Rango2 está completamente dentro del período de Rango1
        boolean caso3 = rango2.getHoraDesde().isAfter(rango1.getHoraDesde()) && rango2.getHoraHasta().isBefore(rango1.getHoraHasta());

        // Caso 4: Rango1 comienza durante el período de Rango2
        boolean caso4 = rango2.getHoraDesde().isBefore(rango1.getHoraDesde()) && rango1.getHoraDesde().isBefore(rango2.getHoraHasta());

        // Caso 5: Rango1 termina durante el período de Rango2
        boolean caso5 = rango2.getHoraDesde().isBefore(rango1.getHoraHasta()) && rango1.getHoraHasta().isBefore(rango2.getHoraHasta());

        // Caso 6: Rango1 está completamente dentro del período de Rango2
        boolean caso6 = rango1.getHoraDesde().isAfter(rango2.getHoraDesde()) && rango1.getHoraHasta().isBefore(rango2.getHoraHasta());

        // Se superponen si alguno de los casos es verdadero
        return caso1 || caso2 || caso3 || caso4 || caso5 || caso6;
    }//ACA TA EL ERROR
    //metodo que persiste o no el objeto en la BD, chequeando superposicion de horarios
    @Override
    public ConfiguracionTiempoRetiro newConfig(ConfiguracionTiempoRetiro configuracion) throws Exception {
    try {


        List<RangoHorario> rangos = configuracion.getRangoHorario();

        for (int i = 0; i < rangos.size(); i++) {

            for (int j = 1; j < rangos.size(); j++) {

                RangoHorario rango1 = rangos.get(i);
                RangoHorario rango2 = rangos.get(j);

                if (seSuperponen(rango1, rango2)) {

                    throw new IllegalArgumentException("Los rangos horarios se superponen.");

                }
            }

        }

        ConfiguracionTiempoRetiro configPersistida = configuracionTiempoRetiroRepository.save(configuracion);

        return configPersistida;
    }catch (Exception e){
        throw new Exception("Error al guardar la configuración en la base de datos", e);
    }
    }
}