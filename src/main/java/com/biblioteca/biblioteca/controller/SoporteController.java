package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.TicketSoporte;
import com.biblioteca.biblioteca.repository.TicketSoporteRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/soporte")
public class SoporteController {

    @Autowired
    private TicketSoporteRepository ticketSoporteRepository;

    @GetMapping
    public String formulario(Model model) {
        model.addAttribute("ticket", new TicketSoporte());
        // templates/soporte/formulario.html
        return "soporte/formulario";
    }

    @PostMapping("/enviar")
    public String enviar(@ModelAttribute TicketSoporte ticket) {
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setEstado("ABIERTO");
        ticketSoporteRepository.save(ticket);
        return "redirect:/soporte?ok";
    }

    @GetMapping("/admin/listado")
    public String listado(Model model) {
        model.addAttribute("tickets", ticketSoporteRepository.findAll());
        // templates/soporte/listado.html
        return "soporte/listado";
    }

    @PostMapping("/cerrar")
    public String cerrar(@RequestParam Long id) {
        TicketSoporte t = ticketSoporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado"));
        t.setEstado("CERRADO");
        ticketSoporteRepository.save(t);
        return "redirect:/soporte/admin/listado";
    }
}
