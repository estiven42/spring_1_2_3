package srping.co.spring_1_2_3.Repositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SolicitudTarjetaRepositorioTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SolicitudTarjetaRepositorio solicitudTarjetaRepositorio;

    @BeforeEach
    void setUp() {
        SolicitudTarjeta solicitud1 = new SolicitudTarjeta(null, "12345678", "Juan", "Perez",
                "juan.perez@example.com", "1234567890", "Calle 123",
                LocalDate.of(1990, 1, 1), LocalDate.of(2021, 1, 1),
                "Empresa S.A.", 5000.0, 1000.0, 2000.0,
                "documento.pdf", "Pendiente");
        entityManager.persist(solicitud1);

        SolicitudTarjeta solicitud2 = new SolicitudTarjeta(null, "87654321", "Maria", "Gomez",
                "maria.gomez@example.com", "0987654321", "Avenida 456",
                LocalDate.of(1985, 5, 15), LocalDate.of(2018, 7, 10),
                "Otro S.A.", 6000.0, 500.0, 1500.0,
                "documento2.pdf", "Aprobada");
        entityManager.persist(solicitud2);

        SolicitudTarjeta solicitud3 = new SolicitudTarjeta(null, "11223344", "Luis", "Rodriguez",
                "luis.rodriguez@example.com", "6677889900", "Carrera 789",
                LocalDate.of(2000, 3, 21), LocalDate.of(2022, 8, 14),
                "Empresa X", 7000.0, 2000.0, 2500.0,
                "documento3.pdf", "Pendiente");
        entityManager.persist(solicitud3);

        entityManager.flush();
    }

    @Test
    void findByEstado_Pendientes_shouldReturnCorrectData() {
        List<SolicitudTarjeta> pendientes = solicitudTarjetaRepositorio.findByEstado("Pendiente");
        assertEquals(2, pendientes.size(), "Debe haber 2 solicitudes pendientes.");
        assertTrue(pendientes.stream().anyMatch(s -> s.getCedula().equals("12345678")),
                "Debe incluir la solicitud de Juan Perez.");
        assertTrue(pendientes.stream().anyMatch(s -> s.getCedula().equals("11223344")),
                "Debe incluir la solicitud de Luis Rodriguez.");
    }

    @Test
    void findByEstado_Aprobadas_shouldReturnCorrectData() {
        List<SolicitudTarjeta> aprobadas = solicitudTarjetaRepositorio.findByEstado("Aprobada");
        assertEquals(1, aprobadas.size(), "Debe haber 1 solicitud aprobada.");
        assertEquals("87654321", aprobadas.get(0).getCedula(),
                "La solicitud aprobada debe ser de Maria Gomez.");
    }

    @Test
    void findByEstado_Rechazadas_shouldReturnEmptyList() {
        List<SolicitudTarjeta> rechazadas = solicitudTarjetaRepositorio.findByEstado("Rechazada");
        assertTrue(rechazadas.isEmpty(), "No debe haber solicitudes rechazadas.");
    }

    @Test
    void findByEstado_Vacio_shouldReturnEmptyList() {
        List<SolicitudTarjeta> vacio = solicitudTarjetaRepositorio.findByEstado("");
        assertTrue(vacio.isEmpty(), "No debe devolver resultados para un estado vacío.");
    }

    @Test
    void findByEstado_Null_shouldThrowException() {
        // Se asegura de que se lance una excepción IllegalArgumentException cuando el estado es nulo
        assertThrows(IllegalArgumentException.class,
                () -> solicitudTarjetaRepositorio.findByEstado(null),
                "Buscar con estado nulo debería lanzar una excepción.");
    }


    @Test
    void saveAndFindSolicitudTarjeta_shouldPersistAndRetrieveData() {
        SolicitudTarjeta nuevaSolicitud = new SolicitudTarjeta(null, "99887766", "Carlos", "Lopez",
                "carlos.lopez@example.com", "5566778899", "Diagonal 321",
                LocalDate.of(1995, 10, 10), LocalDate.of(2020, 5, 5),
                "Nueva Empresa", 8000.0, 3000.0, 2500.0,
                "documento4.pdf", "Pendiente");
        SolicitudTarjeta guardada = solicitudTarjetaRepositorio.save(nuevaSolicitud);

        assertNotNull(guardada.getId(), "La nueva solicitud debe tener un ID generado.");
        assertEquals("Carlos", guardada.getNombre(), "El nombre debe ser Carlos.");

        List<SolicitudTarjeta> pendientes = solicitudTarjetaRepositorio.findByEstado("Pendiente");
        assertTrue(pendientes.stream().anyMatch(s -> s.getCedula().equals("99887766")),
                "Debe incluir la nueva solicitud de Carlos Lopez.");
    }

    @Test
    void deleteSolicitudTarjeta_shouldRemoveData() {
        List<SolicitudTarjeta> pendientes = solicitudTarjetaRepositorio.findByEstado("Pendiente");
        SolicitudTarjeta solicitudAEliminar = pendientes.get(0);
        solicitudTarjetaRepositorio.delete(solicitudAEliminar);

        List<SolicitudTarjeta> pendientesActualizados = solicitudTarjetaRepositorio.findByEstado("Pendiente");
        assertEquals(1, pendientesActualizados.size(), "Debe quedar solo 1 solicitud pendiente.");
        assertFalse(pendientesActualizados.stream().anyMatch(s -> s.getCedula().equals(solicitudAEliminar.getCedula())),
                "La solicitud eliminada no debe estar presente.");
    }
}
