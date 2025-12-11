package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Usuario;
import com.biblioteca.biblioteca.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // ---------- REGISTRO ----------

    @GetMapping("/registro/nuevo")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        // templates/Registro/nuevo.html
        return "Registro/nuevo";
    }

    @PostMapping("/registro/guardar")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {

        Optional<Usuario> u1 = usuarioService.buscarPorUsername(usuario.getUsername());
        if (u1.isPresent()) {
            model.addAttribute("error", "Nombre de usuario ya existe");
            return "Registro/nuevo";
        }

        Optional<Usuario> u2 = usuarioService.buscarPorEmail(usuario.getEmail());
        if (u2.isPresent()) {
            model.addAttribute("error", "Correo ya registrado");
            return "Registro/nuevo";
        }

        usuarioService.registrarNuevoUsuario(usuario);
        return "redirect:/login?registrado";
    }

    // ---------- LOGIN / LOGOUT ----------

    @GetMapping("/login")
    public String mostrarLogin() {
        // templates/login.html
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Optional<Usuario> opt = usuarioService.buscarPorUsername(username);
        if (opt.isEmpty() || !opt.get().getPassword().equals(password)) {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }

        session.setAttribute("usuarioLogueado", opt.get());
        return "redirect:/catalogo/listado";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    // ---------- RECUPERAR / RESTABLECER PASSWORD ----------

    @GetMapping("/recuperar")
    public String mostrarRecuperar() {
        // templates/Auth/recuperar.html
        return "Auth/recuperar";
    }

    @PostMapping("/recuperar")
    public String procesarRecuperar(@RequestParam String email, Model model) {
        try {
            String token = usuarioService.generarTokenRecuperacion(email);
            model.addAttribute("token", token);
            // templates/Auth/token-generado.html
            return "Auth/token-generado";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Auth/recuperar";
        }
    }

    @GetMapping("/restablecer")
    public String mostrarRestablecer(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        // templates/Auth/restablecer.html
        return "Auth/restablecer";
    }

    @PostMapping("/restablecer")
    public String procesarRestablecer(@RequestParam String token,
                                      @RequestParam String nuevaPassword,
                                      Model model) {
        try {
            usuarioService.restablecerConToken(token, nuevaPassword);
            return "redirect:/login?reseteado";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("token", token);
            return "Auth/restablecer";
        }
    }

    // ---------- PERFIL ----------

    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", u);
        // templates/Usuario/perfil.html
        return "Usuario/perfil";
    }

    @PostMapping("/perfil/guardar")
    public String actualizarPerfil(@ModelAttribute Usuario usuario,
                                   HttpSession session) {
        usuarioService.actualizarPerfil(usuario);
        session.setAttribute("usuarioLogueado", usuario);
        return "redirect:/perfil?ok";
    }
}
