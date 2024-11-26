package srping.co.spring_1_2_3.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srping.co.spring_1_2_3.Entidades.GestionEntidad;
import srping.co.spring_1_2_3.Repositorio.GestionEntidadRepositorio;
import srping.co.spring_1_2_3.Servicios.GestionSolicitudServicio;

import java.util.List;
import java.util.Optional;

@Service
public class GestionSolicitudServicioImpl implements GestionSolicitudServicio {

    @Autowired
    private GestionEntidadRepositorio gestionEntidadRepositorio;

    @Override
    public List<GestionEntidad> obtenerSolicitudesPorEstado(GestionEntidad.EstadoSolicitud estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        return gestionEntidadRepositorio.findByEstado(estado);
    }

    @Override
    public GestionEntidad obtenerSolicitudPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la solicitud no puede ser nulo.");
        }
        return gestionEntidadRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada para el ID: " + id));
    }

    @Override
    public GestionEntidad aprobarSolicitud(Long id) {
        return actualizarEstadoSolicitud(id, GestionEntidad.EstadoSolicitud.APROBADA);
    }

    @Override
    public GestionEntidad rechazarSolicitud(Long id) {
        return actualizarEstadoSolicitud(id, GestionEntidad.EstadoSolicitud.RECHAZADA);
    }

    private GestionEntidad actualizarEstadoSolicitud(Long id, GestionEntidad.EstadoSolicitud estado) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la solicitud no puede ser nulo.");
        }

        Optional<GestionEntidad> solicitud = gestionEntidadRepositorio.findById(id);
        if (solicitud.isPresent()) {
            GestionEntidad solicitudEntidad = solicitud.get();
            if (solicitudEntidad.getEstado() == GestionEntidad.EstadoSolicitud.APROBADA ||
                    solicitudEntidad.getEstado() == GestionEntidad.EstadoSolicitud.RECHAZADA) {
                throw new RuntimeException("La solicitud ya ha sido procesada.");
            }

            solicitudEntidad.setEstado(estado);
            return gestionEntidadRepositorio.save(solicitudEntidad);
        } else {
            throw new RuntimeException("Solicitud no encontrada para el ID: " + id);
        }
    }
}
