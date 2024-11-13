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

    // Mostrar la página principal de gestión de solicitudes
    @GetMapping("/GestionSolicitudes")
    public String mostrarGestion(Model model) {
        model.addAttribute("mensaje", "Bienvenido a la administración de solicitudes.");
        return "GestionSolicitudes"; // Asegúrate de tener esta vista en el directorio de plantillas
    }

    // Ruta para obtener solicitudes por estado
    @PostMapping("/estado/{estado}")
    public String obtenerSolicitudesPorEstado(@PathVariable String estado, Model model) {
        try {
            GestionEntidad.EstadoSolicitud estadoSolicitud = GestionEntidad.EstadoSolicitud.valueOf(estado.toUpperCase());
            List<GestionEntidad> solicitudes = gestionSolicitudServicio.obtenerSolicitudesPorEstado(estadoSolicitud);
            model.addAttribute("solicitudes", solicitudes);
            return "GestionSolicitudes"; // Devuelve a la misma vista con la lista de solicitudes
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Estado no válido.");
            return "GestionSolicitudes";
        }
    }

    // Ruta para obtener solicitud por ID
    @PostMapping("/solicitud/{id}")
    public String obtenerSolicitudPorId(@PathVariable Long id, Model model) {
        try {
            GestionEntidad solicitud = gestionSolicitudServicio.obtenerSolicitudPorId(id);
            model.addAttribute("solicitud", solicitud);
            return "DetalleSolicitud"; // Devuelve a una vista de detalle de solicitud
        } catch (RuntimeException e) {
            model.addAttribute("error", "Solicitud no encontrada.");
            return "GestionSolicitudes";
        }
    }

    // Ruta para aprobar solicitud
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

    // Ruta para rechazar solicitud
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
