package Base.Ecommerce.Repositories;

import Base.Ecommerce.Entity.ConfiguracionTiempoRetiro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Repository
public interface ConfiguracionTiempoRetiroRepository extends BaseRepository<ConfiguracionTiempoRetiro,Long>{

    @Query(
            value = "SELECT c.*, d.nombre as nombre_dia FROM configuracion_tiempo_retiro c JOIN dia d ON c.id_dia = d.id WHERE c.fecha_desde <= :fechaActual AND c.fecha_hasta >= :fechaActual AND d.nombre = :nombreDia",
            nativeQuery = true
    )

    ConfiguracionTiempoRetiro findActual(@Param("fechaActual")LocalDate fechaActual, @Param("nombreDia") String nombreDia);
}
