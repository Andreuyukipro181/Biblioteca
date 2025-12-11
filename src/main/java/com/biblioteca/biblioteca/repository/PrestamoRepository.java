package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.Prestamo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuario_Id(Long idUsuario);

    long countByUsuario_IdAndEstado(Long idUsuario, String estado);

    List<Prestamo> findByFechaDevolucionBetween(LocalDate desde, LocalDate hasta);
}
