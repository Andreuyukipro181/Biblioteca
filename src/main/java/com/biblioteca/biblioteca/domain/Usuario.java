package com.biblioteca.biblioteca.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean activo = true;

    // Nivel de membresía (1,2,3)
    private Integer nivelMembresia = 1;

    private LocalDate fechaVencimientoMembresia;

    // Datos de perfil
    private String nombreCompleto;
    private String telefono;
    private String direccion;

    // Recuperación de contraseña
    private String tokenRecuperacion;
    private LocalDate tokenRecuperacionExpira;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> roles = new HashSet<>();
}
