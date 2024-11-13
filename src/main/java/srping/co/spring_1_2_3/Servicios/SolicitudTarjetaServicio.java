package srping.co.spring_1_2_3.Servicios;

import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;

public interface SolicitudTarjetaServicio {
    SolicitudTarjeta crearSolicitud(SolicitudTarjeta solicitud);
    SolicitudTarjeta obtenerSolicitudPorId(Long id);
}
