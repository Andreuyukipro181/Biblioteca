package com.biblioteca.biblioteca.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    public void enviarNotificacion(String destino, String asunto, String mensaje) {
        // Stub: aquí se integraría correo real
        log.info("Notificación a {} - {}: {}", destino, asunto, mensaje);
    }
}
