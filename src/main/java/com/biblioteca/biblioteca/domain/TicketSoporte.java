package com.biblioteca.biblioteca.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticket_soporte")
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String asunto;

    @Column(length = 1000)
    private String descripcion;

    private LocalDateTime fechaCreacion;

    private String estado; // ABIERTO / CERRADO
}
