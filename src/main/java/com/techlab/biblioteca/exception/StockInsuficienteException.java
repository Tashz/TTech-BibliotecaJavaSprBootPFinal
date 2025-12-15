package com.techlab.biblioteca.exception;

// Hereda de RuntimeException para que evitar poner try-catch en todos lados
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}