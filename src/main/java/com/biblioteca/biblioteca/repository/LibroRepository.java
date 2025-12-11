package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByActivoTrue();

    List<Libro> findByActivoTrueAndTituloContainingIgnoreCase(String titulo);
}
