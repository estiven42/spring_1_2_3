package srping.co.spring_1_2_3.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import srping.co.spring_1_2_3.Entidades.SolicitudTarjeta;

@Repository
public interface SolicitudTarjetaRepositorio extends JpaRepository<SolicitudTarjeta, Long> {
    // Puedes agregar métodos personalizados aquí si necesitas consultas adicionales
}
