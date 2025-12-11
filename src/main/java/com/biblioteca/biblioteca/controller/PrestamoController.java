package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.repository.LibroRepository;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import com.biblioteca.biblioteca.service.PrestamoService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/nuevo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("libros", libroRepository.findAll());
        model.addAttribute("prestamos", prestamoService.listarPrestamos());

        // templates/prestamo/listado.html
        return "prestamo/listado";
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("libroId") Long libroId,
            @RequestParam("fechaPrestamo")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPrestamo,
            @RequestParam("fechaDevolucion")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucion,
            @RequestParam(name = "descuento", required = false) String descuento,
            @RequestParam(name = "direccionEntrega", required = false) String direccionEntrega,
            @RequestParam(name = "referenciaEntrega", required = false) String referenciaEntrega,
            @RequestParam(name = "tipoEntrega", required = false) String tipoEntrega,
            @RequestParam(name = "costoEnvio", required = false) Double costoEnvio
    ) {

        prestamoService.crearPrestamo(
                usuarioId, libroId, fechaPrestamo, fechaDevolucion,
                descuento, direccionEntrega, referenciaEntrega,
                tipoEntrega, costoEnvio
        );

        return "redirect:/prestamo/nuevo";
    }

    @PostMapping("/devolver")
    public String devolver(
            @RequestParam("prestamoId") Long prestamoId,
            @RequestParam("fechaDevolucionReal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucionReal) {

        prestamoService.registrarDevolucion(prestamoId, fechaDevolucionReal);
        return "redirect:/prestamo/nuevo";
    }

    @PostMapping("/renovar")
    public String renovar(
            @RequestParam("prestamoId") Long prestamoId,
            @RequestParam("nuevaFechaDevolucion")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFechaDevolucion) {

        prestamoService.renovarPrestamo(prestamoId, nuevaFechaDevolucion);
        return "redirect:/prestamo/nuevo";
    }

    @GetMapping("/historial/{idUsuario}")
    public String historial(@PathVariable Long idUsuario, Model model) {
        model.addAttribute("prestamos",
                prestamoService.listarPrestamosPorUsuario(idUsuario));
        // templates/prestamo/historial.html
        return "prestamo/historial";
    }
}
