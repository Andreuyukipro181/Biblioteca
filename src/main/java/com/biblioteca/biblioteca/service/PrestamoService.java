package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.domain.Prestamo;
import com.biblioteca.biblioteca.domain.PrestamoItem;
import com.biblioteca.biblioteca.domain.Usuario;
import com.biblioteca.biblioteca.repository.LibroRepository;
import com.biblioteca.biblioteca.repository.PrestamoItemRepository;
import com.biblioteca.biblioteca.repository.PrestamoRepository;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoItemRepository prestamoItemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Crea un préstamo simple de 1 libro para 1 usuario.
     * Incluye campos de envío y descuento.
     */
    @Transactional
    public void crearPrestamo(Long idUsuario,
                              Long idLibro,
                              LocalDate fechaPrestamo,
                              LocalDate fechaDevolucion,
                              String descuento,
                              String direccionEntrega,
                              String referenciaEntrega,
                              String tipoEntrega,
                              Double costoEnvio) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        if (libro.getStock() == null || libro.getStock() <= 0) {
            throw new IllegalStateException("No hay stock disponible para el libro seleccionado");
        }

        // Disminuir stock en 1
        libro.setStock(libro.getStock() - 1);
        libroRepository.save(libro);

        // Crear préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setEstado("VIGENTE");

        // Descuento: si viene por parámetro se respeta, si no se calcula por nivel de membresía
        if (descuento != null && !descuento.isBlank()) {
            prestamo.setDescuento(descuento);
        } else {
            // Ejemplo sencillo: según nivel de membresía
            Integer nivel = usuario.getNivelMembresia();
            if (nivel != null && nivel > 0) {
                // Nivel 1: 5%, nivel 2: 10%, etc. (solo un ejemplo para mostrar el texto)
                int porcentaje = nivel * 5;
                prestamo.setDescuento(porcentaje + "%");
            } else {
                prestamo.setDescuento("0%");
            }
        }

        // Datos de entrega / envío
        prestamo.setDireccionEntrega(direccionEntrega);
        prestamo.setReferenciaEntrega(referenciaEntrega);
        prestamo.setTipoEntrega(tipoEntrega);
        prestamo.setCostoEnvio(costoEnvio != null ? costoEnvio : 0.0);

        // Multas iniciales en cero
        prestamo.setMultaCalculada(0.0);
        prestamo.setMultaPagada(0.0);

        // Crear item
        PrestamoItem item = new PrestamoItem();
        item.setPrestamo(prestamo);
        item.setLibro(libro);
        item.setCantidad(1);

        List<PrestamoItem> items = new ArrayList<>();
        items.add(item);
        prestamo.setItems(items);

        // Guardar préstamo (items se guardan por cascada)
        prestamoRepository.save(prestamo);
    }

    /**
     * Registrar devolución de un préstamo.
     * Calcula multa si la fecha real es posterior a la fecha de devolución pactada.
     */
    @Transactional
    public void registrarDevolucion(Long prestamoId, LocalDate fechaDevolucionReal) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        prestamo.setFechaDevolucionReal(fechaDevolucionReal);

        // Cálculo sencillo de multa: 500 colones por día de atraso (ejemplo)
        if (fechaDevolucionReal != null &&
                prestamo.getFechaDevolucion() != null &&
                fechaDevolucionReal.isAfter(prestamo.getFechaDevolucion())) {

            long diasAtraso = ChronoUnit.DAYS.between(
                    prestamo.getFechaDevolucion(), fechaDevolucionReal);

            Double multa = diasAtraso * 500.0;
            prestamo.setMultaCalculada(multa);
            prestamo.setEstado("ATRASADO");
        } else {
            prestamo.setMultaCalculada(0.0);
            prestamo.setEstado("DEVUELTO");
        }

        // Cuando se devuelve asumimos que la multa se paga inmediatamente
        prestamo.setMultaPagada(prestamo.getMultaCalculada());

        prestamoRepository.save(prestamo);

        // Opcional: devolver stock del libro
        if (prestamo.getItems() != null) {
            for (PrestamoItem it : prestamo.getItems()) {
                Libro libro = it.getLibro();
                if (libro != null) {
                    Integer stock = libro.getStock() != null ? libro.getStock() : 0;
                    libro.setStock(stock + it.getCantidad());
                    libroRepository.save(libro);
                }
            }
        }
    }

    /**
     * Renovar fecha de devolución de un préstamo.
     */
    @Transactional
    public void renovarPrestamo(Long prestamoId, LocalDate nuevaFechaDevolucion) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        prestamo.setFechaDevolucion(nuevaFechaDevolucion);
        prestamo.setEstado("VIGENTE");
        prestamoRepository.save(prestamo);
    }

    /**
     * Lista todos los préstamos.
     */
    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    /**
     * Lista los préstamos de un usuario específico.
     */
    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return prestamoRepository.findAll()
                .stream()
                .filter(p -> p.getUsuario() != null
                        && p.getUsuario().getId().equals(usuario.getId()))
                .toList();
    }
}
