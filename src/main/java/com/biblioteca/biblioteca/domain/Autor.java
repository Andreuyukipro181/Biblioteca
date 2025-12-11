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

@Data @Entity @Table(name="autor")
public class Autor {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false,length=120)
  private String nombre;
  @Column(nullable=false) private Boolean activo=true;
}
