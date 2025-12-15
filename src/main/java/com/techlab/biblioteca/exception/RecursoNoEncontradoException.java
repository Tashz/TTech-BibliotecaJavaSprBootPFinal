package com.techlab.biblioteca.exception;

// Esta excepción la lanzaremos cuando busquemos algo que no existe en la DB
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}