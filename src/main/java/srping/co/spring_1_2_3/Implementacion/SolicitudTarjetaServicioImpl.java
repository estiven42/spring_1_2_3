package srping.co.spring_1_2_3.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;
import srping.co.spring_1_2_3.Repositorio.SolicitudTarjetaRepositorio;
import java.util.List;

@Service
public class SolicitudTarjetaServicioImpl implements SolicitudTarjetaServicio {

    @Autowired
    private SolicitudTarjetaRepositorio repositorio;

    @Override
    @Transactional
    public void crearSolicitud(SolicitudTarjeta solicitud) {
        // Validaciones adicionales antes de guardar
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        // Verificar si ya existe una solicitud con la misma cédula
        SolicitudTarjeta solicitudExistente = repositorio.findByCedula(solicitud.getCedula());
        if (solicitudExistente != null) {
            throw new RuntimeException("Ya existe una solicitud con esta cédula");
        }

        repositorio.save(solicitud);
    }

    @Override
    public SolicitudTarjeta obtenerSolicitudPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));
    }

    @Override
    public List<SolicitudTarjeta> obtenerTodasLasSolicitudes() {
        return repositorio.findAll();
    }

    @Override
    public List<SolicitudTarjeta> obtenerSolicitudesPorEstado(String estado) {
        return repositorio.findByEstado(estado);
    }
}