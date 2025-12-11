package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.domain.Resena;
import com.biblioteca.biblioteca.domain.Usuario;
import com.biblioteca.biblioteca.repository.LibroRepository;
import com.biblioteca.biblioteca.repository.ResenaRepository;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void crearResena(Long idUsuario, Long idLibro, Integer estrellas, String comentario) {
        Usuario u = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Libro l = libroRepository.findById(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        Resena r = new Resena();
        r.setUsuario(u);
        r.setLibro(l);
        r.setEstrellas(estrellas);
        r.setComentario(comentario);

        resenaRepository.save(r);
    }

    @Transactional(readOnly = true)
    public List<Resena> listarPorLibro(Long idLibro) {
        return resenaRepository.findAll().stream()
                .filter(r -> r.getLibro().getId().equals(idLibro))
                .toList();
    }
}
