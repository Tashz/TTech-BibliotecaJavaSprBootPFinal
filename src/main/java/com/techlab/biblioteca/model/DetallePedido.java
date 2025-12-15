package com.techlab.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muchos detalles pueden apuntar al mismo libro
    @JoinColumn(name = "libro_id")
    private Libro libro;

    private Integer cantidad;

    private BigDecimal precioUnitario; // Guardamos el precio al momento de la compra

    private BigDecimal subtotal; // cantidad * precioUnitario

    private String tipo; // "VENTA" o "ALQUILER"
}