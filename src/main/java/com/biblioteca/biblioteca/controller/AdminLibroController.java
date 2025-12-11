package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.repository.AutorRepository;
import com.biblioteca.biblioteca.repository.GeneroRepository;
import com.biblioteca.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/libros")
public class AdminLibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        // templates/adminLibros/listado.html
        return "adminLibros/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("autores", autorRepository.findAll());
        model.addAttribute("generos", generoRepository.findAll());
        // templates/adminLibros/formulario.html
        return "adminLibros/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Libro libro) {
        libroRepository.save(libro);
        return "redirect:/admin/libros/listado";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        model.addAttribute("libro", libro);
        model.addAttribute("autores", autorRepository.findAll());
        model.addAttribute("generos", generoRepository.findAll());
        return "adminLibros/formulario";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id) {
        libroRepository.deleteById(id);
        return "redirect:/admin/libros/listado";
    }
}
