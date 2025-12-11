package com.biblioteca.biblioteca.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con usuario (columna id_usuario en la tabla)
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    // Fecha de devolución real (cuando el usuario lo devuelve)
    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    // VIGENTE / DEVUELTO / ATRASADO, etc.
    @Column(nullable = false, length = 20)
    private String estado;

    // Descuento aplicado al préstamo (por membresía u otros motivos)
    @Column(length = 50)
    private String descuento;

    // ------ Campos para la entrega/envío del libro ---------

    @Column(name = "costo_envio")
    private Double costoEnvio;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @Column(name = "referencia_entrega")
    private String referenciaEntrega;

    // PRESENCIAL / DOMICILIO / OTRO
    @Column(name = "tipo_entrega", length = 50)
    private String tipoEntrega;

    // ------ Multas por atraso ------------------------------

    @Column(name = "multa_calculada")
    private Double multaCalculada;

    @Column(name = "multa_pagada")
    private Double multaPagada;

    // ------ Detalle (uno a muchos) -------------------------

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrestamoItem> items;
}
