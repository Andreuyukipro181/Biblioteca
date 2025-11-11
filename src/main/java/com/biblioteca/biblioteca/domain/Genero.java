/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.biblioteca.domain;

/**
 *
 * @author Uyuki
 */
import jakarta.persistence.*;
import lombok.*;

@Data @Entity @Table(name="genero")
public class Genero {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false,length=80)
  private String nombre;
  @Column(nullable=false)
  private Boolean activo=true;
}

