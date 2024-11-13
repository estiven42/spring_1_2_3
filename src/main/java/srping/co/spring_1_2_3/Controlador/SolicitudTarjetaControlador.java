package srping.co.spring_1_2_3.Controlador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;
import srping.co.spring_1_2_3.Servicios.SolicitudTarjetaServicio;
@Controller
public class SolicitudTarjetaControlador {

    @Autowired
    private SolicitudTarjetaServicio solicitudTarjetaServicio;

    private final String uploadDir = "uploads/";

    // Muestra el formulario de solicitud de tarjeta
    @GetMapping("/SolicitarTarjeta")
    public String mostrarFormulario() {
        return "SolicitarTarjeta";
    }

    // Procesa la solicitud de tarjeta
    @PostMapping("/SolicitarTarjeta")
    public String procesarSolicitud(
            @RequestParam String cedula,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String direccion,
            @RequestParam LocalDate fechaNacimiento,
            @RequestParam LocalDate fechaExpedicion,
            @RequestParam String empresa,
            @RequestParam Double ingresoMensual,
            @RequestParam Double otrosIngresos,
            @RequestParam Double gastosMensuales,
            @RequestParam MultipartFile documentoIdentidad,
            Model model) {

        // Validación básica de campos
        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            return "SolicitarTarjeta";
        }

        try {
            // Validar y guardar el archivo PDF
            if (!documentoIdentidad.isEmpty() && documentoIdentidad.getContentType().equals("application/pdf")) {
                // Crear directorio si no existe
                Path rutaDirectorio = Paths.get(uploadDir);
                if (!Files.exists(rutaDirectorio)) {
                    Files.createDirectories(rutaDirectorio);
                }

                // Guardar el archivo PDF
                String documentoNombre = documentoIdentidad.getOriginalFilename();
                Path rutaArchivo = Paths.get(uploadDir, documentoNombre);
                Files.write(rutaArchivo, documentoIdentidad.getBytes());

                // Crear la solicitud de tarjeta
                SolicitudTarjeta solicitud = new SolicitudTarjeta();
                solicitud.setCedula(cedula);
                solicitud.setNombre(nombre);
                solicitud.setApellido(apellido);
                solicitud.setCorreo(correo);
                solicitud.setTelefono(telefono);
                solicitud.setDireccion(direccion);
                solicitud.setFechaNacimiento(fechaNacimiento);
                solicitud.setFechaExpedicion(fechaExpedicion);
                solicitud.setEmpresa(empresa);
                solicitud.setIngresoMensual(ingresoMensual);
                solicitud.setOtrosIngresos(otrosIngresos);
                solicitud.setGastosMensuales(gastosMensuales);
                solicitud.setDocumentoIdentidad(documentoNombre);

                // Guardar la solicitud en la base de datos
                solicitudTarjetaServicio.crearSolicitud(solicitud);

                // Agregar un mensaje de éxito al modelo
                model.addAttribute("mensaje", "Solicitud enviada con éxito. Está en proceso de revisión.");
                return "AdministracionTarjeta";
            } else {
                model.addAttribute("error", "El archivo debe ser un documento PDF.");
                return "SolicitarTarjeta";
            }

        } catch (IOException e) {
            model.addAttribute("error", "Hubo un problema al cargar el documento. Intenta nuevamente.");
            return "SolicitarTarjeta";
        }
    }

    // Página de administración de tarjetas
    @GetMapping("/AdministracionTarjeta")
    public String administracionTarjeta(Model model) {
        model.addAttribute("mensaje", "Tu solicitud está en proceso.");
        return "AdministracionTarjeta";
    }
}
