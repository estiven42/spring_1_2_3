package srping.co.spring_1_2_3.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srping.co.spring_1_2_3.Entidades.GestionEntidad;
import srping.co.spring_1_2_3.Entidades.GestionEntidad.EstadoSolicitud;

import java.util.List;

@Repository
public interface GestionEntidadRepositorio extends JpaRepository<GestionEntidad, Long> {
    List<GestionEntidad> findByEstado(GestionEntidad.EstadoSolicitud estado);
}
