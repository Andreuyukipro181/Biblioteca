package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/admin/panel")
    public String panel(Model model) {
        model.addAttribute("totalPrestamos", reporteService.totalPrestamos());
        model.addAttribute("totalLibros", reporteService.totalLibros());
        model.addAttribute("totalUsuarios", reporteService.totalUsuarios());
        // templates/adminLibros/panel.html
        return "adminLibros/panel";
    }
}
