package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @GetMapping("/listado")
    public String listado(
            @RequestParam(name = "q", required = false) String q,
            Model model) {

        var libros = catalogoService.getLibros(true); // solo activos

        model.addAttribute("libros", libros);
        model.addAttribute("totalLibros", libros.size());
        model.addAttribute("query", q);

        // Vista física: src/main/resources/templates/libro/listado.html
        return "libro/listado";
    }
}
