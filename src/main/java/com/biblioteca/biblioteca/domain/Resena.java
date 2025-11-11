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

@Data @Entity @Table(name="resena",
  uniqueConstraints=@UniqueConstraint(columnNames={"id_usuario","id_libro"}))
public class Resena {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @ManyToOne @JoinColumn(name="id_usuario") private Usuario usuario;
  @ManyToOne @JoinColumn(name="id_libro") private Libro libro;
  private Integer estrellas; // 1..5
  @Column(length=500) private String comentario;
}
