package srping.co.spring_1_2_3.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import srping.co.spring_1_2_3.Entidades.GestionEntidad;
import srping.co.spring_1_2_3.Servicios.GestionSolicitudServicio;

import java.util.List;

@Controller
public class GestionSolicitudControlador {

    @Autowired
    private GestionSolicitudServicio gestionSolicitudServicio;

    @GetMapping("/GestionSolicitudes")
    public String mostrarGestion(Model model) {
        model.addAttribute("mensaje", "Bienvenido a la administración de solicitudes.");
        return "GestionSolicitudes";
    }

    @PostMapping("/estado/{estado}")
    public String obtenerSolicitudesPorEstado(@PathVariable String estado, Model model) {
        try {
            GestionEntidad.EstadoSolicitud estadoSolicitud = GestionEntidad.EstadoSolicitud.valueOf(estado.toUpperCase());
            List<GestionEntidad> solicitudes = gestionSolicitudServicio.obtenerSolicitudesPorEstado(estadoSolicitud);
            model.addAttribute("solicitudes", solicitudes);
            return "GestionSolicitudes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Estado no válido.");
            return "GestionSolicitudes";
        }
    }

    @PostMapping("/solicitud/{id}")
    public String obtenerSolicitudPorId(@PathVariable Long id, Model model) {
        try {
            GestionEntidad solicitud = gestionSolicitudServicio.obtenerSolicitudPorId(id);
            model.addAttribute("solicitud", solicitud);
            return "DetalleSolicitud";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Solicitud no encontrada.");
            return "GestionSolicitudes";
        }
    }

    @PostMapping("/aprobar/{id}")
    public String aprobarSolicitud(@PathVariable Long id, Model model) {
        try {
            GestionEntidad solicitud = gestionSolicitudServicio.aprobarSolicitud(id);
            model.addAttribute("solicitud", solicitud);
            model.addAttribute("mensaje", "Solicitud aprobada.");
            return "GestionSolicitudes";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al aprobar la solicitud.");
            return "GestionSolicitudes";
        }
    }

    @PostMapping("/rechazar/{id}")
    public String rechazarSolicitud(@PathVariable Long id, Model model) {
        try {
            GestionEntidad solicitud = gestionSolicitudServicio.rechazarSolicitud(id);
            model.addAttribute("solicitud", solicitud);
            model.addAttribute("mensaje", "Solicitud rechazada.");
            return "GestionSolicitudes";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al rechazar la solicitud.");
            return "GestionSolicitudes";
        }
    }
}
