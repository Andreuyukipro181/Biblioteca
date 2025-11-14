package com.biblioteca.biblioteca.service;
import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.repository.LibroRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CatalogoService {
    @Autowired
    private LibroRepository libroRepository;
    
    @Transactional(readOnly=true)
    public List<Libro> getLibro(boolean activo) {
        if (activo) {
            return libroRepository.findByActivoTrue();
        }
        return libroRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Libro> getLibro(Integer idLibro) {
        return libroRepository.findById(idLibro);
    }
}
