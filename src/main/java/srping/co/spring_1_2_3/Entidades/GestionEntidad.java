package srping.co.spring_1_2_3.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Definición de la clase entidad GestionEntidad
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GestionSolicitudes")
public class GestionEntidad {

    // ID único de la solicitud
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del cliente que solicita la tarjeta
    @NotNull
    @Size(min = 1, max = 100)
    private String nombreCliente;

    // Correo electrónico del cliente
    @Email
    private String correoElectronico;

    // Fecha en la que se hizo la solicitud
    private LocalDate fechaSolicitud;

    // Estado de la solicitud (Pendiente, Aprobada, Rechazada)
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    // Motivo de la solicitud, si lo hay
    private String motivo;

    // Enum para los posibles estados de la solicitud
    public enum EstadoSolicitud {
        PENDIENTE, APROBADA, RECHAZADA
    }
}
