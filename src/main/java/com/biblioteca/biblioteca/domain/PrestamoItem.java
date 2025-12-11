/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.biblioteca.domain;

/**
 *
 * @author Uyuki
 */
import jakarta.persistence.*; import lombok.*;

@Data @Entity @Table(name="prestamo_item")
public class PrestamoItem {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @ManyToOne @JoinColumn(name="id_prestamo") private Prestamo prestamo;
  @ManyToOne @JoinColumn(name="id_libro") private Libro libro;
  private Integer cantidad;
}
