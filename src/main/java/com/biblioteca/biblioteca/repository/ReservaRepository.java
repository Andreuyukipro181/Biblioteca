package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.Reserva;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByLibro_IdOrderByFechaReservaAsc(Long idLibro);
}
