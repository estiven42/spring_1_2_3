package srping.co.spring_1_2_3.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;

import java.util.List;

@Repository
public interface SolicitudTarjetaRepositorio extends JpaRepository<SolicitudTarjeta, Long> {
    List<SolicitudTarjeta> findByEstado(String estado);

    // Método adicional para buscar por cédula
    SolicitudTarjeta findByCedula(String cedula);
}