package srping.co.spring_1_2_3.Implementacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;
import srping.co.spring_1_2_3.Repositorio.SolicitudTarjetaRepositorio;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolicitudTarjetaServicioImplTest {

    @Mock
    private SolicitudTarjetaRepositorio repositorio;

    @InjectMocks
    private srping.co.spring_1_2_3.Servicios.SolicitudTarjetaServicioImpl servicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearSolicitud_debeGuardarSolicitudEnRepositorio() {
        // Arrange
        SolicitudTarjeta solicitud = new SolicitudTarjeta();
        solicitud.setId(1L);
        solicitud.setNombre("Juan Pérez");

        // Act
        servicio.crearSolicitud(solicitud);

        // Assert
        verify(repositorio, times(1)).save(solicitud);
    }

    @Test
    void obtenerSolicitudPorId_debeRetornarSolicitudSiExiste() {
        // Arrange
        SolicitudTarjeta solicitud = new SolicitudTarjeta();
        solicitud.setId(1L);
        when(repositorio.findById(1L)).thenReturn(Optional.of(solicitud));

        // Act
        SolicitudTarjeta resultado = servicio.obtenerSolicitudPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repositorio, times(1)).findById(1L);
    }

    @Test
    void obtenerSolicitudPorId_debeRetornarNullSiNoExiste() {
        // Arrange
        when(repositorio.findById(1L)).thenReturn(Optional.empty());

        // Act
        SolicitudTarjeta resultado = servicio.obtenerSolicitudPorId(1L);

        // Assert
        assertNull(resultado);
        verify(repositorio, times(1)).findById(1L);
    }
}
