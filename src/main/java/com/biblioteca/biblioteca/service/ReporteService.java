package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.repository.LibroRepository;
import com.biblioteca.biblioteca.repository.PrestamoRepository;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public long totalPrestamos() {
        return prestamoRepository.count();
    }

    public long totalLibros() {
        return libroRepository.count();
    }

    public long totalUsuarios() {
        return usuarioRepository.count();
    }
}
