package srping.co.spring_1_2_3.Servicios;

import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;

import java.util.List;

public interface SolicitudTarjetaServicio {
    void crearSolicitud(SolicitudTarjeta solicitud);
    SolicitudTarjeta obtenerSolicitudPorId(Long id);
    List<SolicitudTarjeta> obtenerTodasLasSolicitudes();
    List<SolicitudTarjeta> obtenerSolicitudesPorEstado(String estado);
}
