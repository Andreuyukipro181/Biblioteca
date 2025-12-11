package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Usuario;
import com.biblioteca.biblioteca.service.CatalogoService;
import com.biblioteca.biblioteca.service.ResenaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ResenaService resenaService;

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var opt = catalogoService.getLibro(id);
        if (opt.isEmpty()) {
            return "redirect:/catalogo/listado";
        }

        var libro = opt.get();
        model.addAttribute("libro", libro);
        model.addAttribute("resenas", resenaService.listarPorLibro(id));

        // templates/libro/detalle.html
        return "libro/detalle";
    }

    @PostMapping("/{id}/resena")
    public String crearResena(@PathVariable Long id,
                              @RequestParam Integer estrellas,
                              @RequestParam String comentario,
                              HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        resenaService.crearResena(usuario.getId(), id, estrellas, comentario);
        return "redirect:/libro/detalle/" + id;
    }
}
