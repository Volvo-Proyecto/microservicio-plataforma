package duoc.cl.plataforma.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import duoc.cl.plataforma.Model.Plataforma;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    Optional<Plataforma> findByNombre(String nombre);
    List<Plataforma> findByNombreContainingIgnoreCase(String nombre);
}
