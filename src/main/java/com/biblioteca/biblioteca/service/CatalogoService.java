package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.repository.LibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoService {

    @Autowired
    private LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public List<Libro> getLibros(boolean soloActivos) {
        if (soloActivos) {
            return libroRepository.findByActivoTrue();
        }
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Libro> getLibro(Long idLibro) {
        return libroRepository.findById(idLibro);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosPorTitulo(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return libroRepository.findByActivoTrue();
        }
        return libroRepository.findByActivoTrueAndTituloContainingIgnoreCase(filtro);
    }
}
