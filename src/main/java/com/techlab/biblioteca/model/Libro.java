package com.techlab.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String autor;

    private String categoria;

    private BigDecimal costoAlquiler;

    private BigDecimal precioVenta;

    private Integer stock;

    private String imagenUrl;
}