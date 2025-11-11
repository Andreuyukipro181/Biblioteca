/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.biblioteca.domain;

/**
 *
 * @author Uyuki
 */
import jakarta.persistence.*; import lombok.*; import java.time.LocalDate; import java.util.List;

@Data @Entity @Table(name="prestamo")
public class Prestamo {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @ManyToOne @JoinColumn(name="id_usuario") private Usuario usuario;
  private LocalDate fechaPrestamo;
  private LocalDate fechaDevolucion;
  private String estado; // VIGENTE/DEVUELTO/ATRASADO
  private String descuento;

  @OneToMany(mappedBy="prestamo", cascade=CascadeType.ALL, orphanRemoval=true)
  private List<PrestamoItem> items;
}
