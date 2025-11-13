package biblioteca.biblioteca.repository;

import biblioteca.biblioteca.domain.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro,Integer>{
    public List<Libro> findByActivoTrue();
    
}
