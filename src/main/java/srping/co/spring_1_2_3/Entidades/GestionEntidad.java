package srping.co.spring_1_2_3.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GestionSolicitudes")
public class GestionEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String nombreCliente;

    @Email
    private String correoElectronico;

    private LocalDate fechaSolicitud;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    private String motivo;

    public enum EstadoSolicitud {
        PENDIENTE, APROBADA, RECHAZADA
    }
}