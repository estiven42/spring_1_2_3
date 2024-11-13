package srping.co.spring_1_2_3.Entidades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitudes_tarjeta")
public class SolicitudTarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitud_seq")
    @SequenceGenerator(name = "solicitud_seq", sequenceName = "solicitud_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String cedula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private LocalDate fechaExpedicion;

    @Column(nullable = false)
    private String empresa;

    @Column(nullable = false)
    private Double ingresoMensual;

    @Column(nullable = false)
    private Double otrosIngresos;

    @Column(nullable = false)
    private Double gastosMensuales;

    @Column(nullable = false)
    private String documentoIdentidad; // Ruta o nombre del archivo PDF

    @Column(nullable = false)
    private String estado = "Pendiente"; // Estado inicial de la solicitud
}
