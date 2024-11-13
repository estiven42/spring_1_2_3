package srping.co.spring_1_2_3.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;
import srping.co.spring_1_2_3.Servicios.SolicitudTarjetaServicio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SolicitudTarjetaControlador {

    @Autowired
    private SolicitudTarjetaServicio solicitudTarjetaServicio;

    @Value("${documento.ruta}")
    private String rutaDocumento;

    @GetMapping("/AdministracionTarjeta")
    public String mostrarAdministracion(Model model) {
        model.addAttribute("solicitudes", solicitudTarjetaServicio.obtenerTodasLasSolicitudes());
        return "AdministracionTarjeta";
    }

    @GetMapping("/SolicitarTarjeta")
    public String mostrarFormularioSolicitud() {
        return "SolicitarTarjeta";
    }

    @PostMapping("/SolicitarTarjeta")
    @ResponseBody
    public ResponseEntity<?> procesarSolicitud(@ModelAttribute SolicitudTarjeta solicitud,
                                               @RequestParam("archivo") MultipartFile archivo) {
        try {
            // Validar campos
            validarCampos(solicitud, archivo);

            // Guardar el archivo PDF si se proporcionó uno
            if (!archivo.isEmpty()) {
                String rutaArchivo = guardarArchivo(archivo);
                solicitud.setDocumentoIdentidad(rutaArchivo);
            }

            // Establecer estado inicial
            solicitud.setEstado("Pendiente");

            // Guardar la solicitud en la base de datos
            solicitudTarjetaServicio.crearSolicitud(solicitud);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    private void validarCampos(SolicitudTarjeta solicitud, MultipartFile archivo) {
        List<String> errores = new ArrayList<>();

        if (solicitud.getCedula() == null || solicitud.getCedula().trim().isEmpty()) {
            errores.add("La cédula es obligatoria");
        }
        if (solicitud.getNombre() == null || solicitud.getNombre().trim().isEmpty()) {
            errores.add("El nombre es obligatorio");
        }
        if (solicitud.getApellido() == null || solicitud.getApellido().trim().isEmpty()) {
            errores.add("El apellido es obligatorio");
        }
        if (solicitud.getCorreo() == null || solicitud.getCorreo().trim().isEmpty()) {
            errores.add("El correo es obligatorio");
        }
        if (solicitud.getFechaNacimiento() == null) {
            errores.add("La fecha de nacimiento es obligatoria");
        }
        if (solicitud.getFechaExpedicion() == null) {
            errores.add("La fecha de expedición es obligatoria");
        }
        if (solicitud.getIngresoMensual() == null || solicitud.getIngresoMensual() <= 0) {
            errores.add("El ingreso mensual debe ser mayor a 0");
        }
        if (solicitud.getGastosMensuales() == null || solicitud.getGastosMensuales() < 0) {
            errores.add("Los gastos mensuales no pueden ser negativos");
        }
        if (archivo.isEmpty()) {
            errores.add("Debe adjuntar un documento de identidad");
        }

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errores));
        }
    }

    private String guardarArchivo(MultipartFile archivo) throws IOException {
        // Crear el directorio si no existe
        File directorio = new File(rutaDocumento);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Generar nombre único para el archivo
        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
        String rutaArchivo = rutaDocumento + File.separator + nombreArchivo;

        // Guardar el archivo
        File file = new File(rutaArchivo);
        archivo.transferTo(file);

        return rutaArchivo;
    }
}