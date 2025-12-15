package com.techlab.biblioteca.exception;

public class OperacionNoPermitidaException extends RuntimeException{
    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
