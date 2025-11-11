/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.biblioteca.domain;

/**
 *
 * @author Uyuki
 */
import jakarta.persistence.*; import lombok.*; import java.util.*;

@Data @Entity @Table(name="usuario")
public class Usuario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false,unique=true) private String username;
  @Column(nullable=false,unique=true) private String email;
  @Column(nullable=false) private String password;
  @Column(nullable=false) private Boolean activo=true;

  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name="usuario_rol",
    joinColumns=@JoinColumn(name="id_usuario"),
    inverseJoinColumns=@JoinColumn(name="id_rol"))
  private Set<Rol> roles=new HashSet<>();
  

}
