package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Usuario;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public Usuario registrarNuevoUsuario(Usuario usuario) {
        usuario.setActivo(true);
        usuario.setNivelMembresia(1);
        usuario.setFechaVencimientoMembresia(LocalDate.now().plusMonths(1));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void actualizarPerfil(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public String generarTokenRecuperacion(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Correo no registrado"));
        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setTokenRecuperacionExpira(LocalDate.now().plusDays(1));
        usuarioRepository.save(usuario);
        return token;
    }

    @Transactional
    public void restablecerConToken(String token, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> token.equals(u.getTokenRecuperacion()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (usuario.getTokenRecuperacionExpira() == null ||
            usuario.getTokenRecuperacionExpira().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Token expirado");
        }

        usuario.setPassword(nuevaPassword);
        usuario.setTokenRecuperacion(null);
        usuario.setTokenRecuperacionExpira(null);
        usuarioRepository.save(usuario);
    }
}
