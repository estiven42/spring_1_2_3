package srping.co.spring_1_2_3.Servicios;

import srping.co.spring_1_2_3.Entidades.GestionEntidad;
import srping.co.spring_1_2_3.Entidades.GestionEntidad.EstadoSolicitud;

import java.util.List;

public interface GestionSolicitudServicio {
    List<GestionEntidad> obtenerSolicitudesPorEstado(EstadoSolicitud estado);
    GestionEntidad obtenerSolicitudPorId(Long id);
    GestionEntidad aprobarSolicitud(Long id);
    GestionEntidad rechazarSolicitud(Long id);
}