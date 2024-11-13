package srping.co.spring_1_2_3.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;
import srping.co.spring_1_2_3.Repositorio.SolicitudTarjetaRepositorio;
import srping.co.spring_1_2_3.Servicios.SolicitudTarjetaServicio;

@Service
public class SolicitudTarjetaServicioImpl implements SolicitudTarjetaServicio {

    @Autowired
    private SolicitudTarjetaRepositorio solicitudTarjetaRepositorio;

    @Override
    public SolicitudTarjeta crearSolicitud(SolicitudTarjeta solicitud) {
        solicitud.setEstado("Pendiente");
        return solicitudTarjetaRepositorio.save(solicitud);
    }

    @Override
    public SolicitudTarjeta obtenerSolicitudPorId(Long id) {
        return solicitudTarjetaRepositorio.findById(id).orElse(null);
    }
}