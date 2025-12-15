package com.techlab.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejar Recurso No Encontrado (Devuelve 404 Not Found)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Object> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("error", "Recurso no encontrado");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // 2. Manejar Stock Insuficiente (Devuelve 400 Bad Request)
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Object> manejarStockInsuficiente(StockInsuficienteException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("error", "Error de validación de negocio");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 3. Manejar cualquier otro error inesperado (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> manejarGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", "Ocurrió un error interno en el servidor");
        body.put("detalle", ex.getMessage()); // Solo para desarrollo, en prod se suele ocultar

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // 4. Manejar Recurso Duplicado (Devuelve 409 Conflict)
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Object> manejarDuplicado(RecursoDuplicadoException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("error", "Conflicto de datos (Duplicado)");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // 5. Manejar Operación No Permitida (Devuelve 409 Conflict)
    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ResponseEntity<Object> manejarOperacionNoPermitida(OperacionNoPermitidaException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("mensaje", ex.getMessage());
        body.put("error", "Operación no permitida por reglas de negocio");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}