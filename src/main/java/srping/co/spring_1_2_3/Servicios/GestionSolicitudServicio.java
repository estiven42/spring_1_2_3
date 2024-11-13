package srping.co.spring_1_2_3.Servicios;

import srping.co.spring_1_2_3.Entidades.GestionEntidad;
import srping.co.spring_1_2_3.Entidades.GestionEntidad.EstadoSolicitud;

import java.util.List;

public interface GestionSolicitudServicio {

    // Obtener solicitudes por estado
    List<GestionEntidad> obtenerSolicitudesPorEstado(EstadoSolicitud estado);

    // Obtener solicitud por ID
    GestionEntidad obtenerSolicitudPorId(Long id);

    // Aprobar solicitud
    GestionEntidad aprobarSolicitud(Long id);

    // Rechazar solicitud
    GestionEntidad rechazarSolicitud(Long id);
}
