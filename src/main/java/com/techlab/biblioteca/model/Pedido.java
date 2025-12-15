package com.techlab.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private BigDecimal total;

    private String estado; // Ej: "PENDIENTE", "COMPLETADO", "CANCELADO"

    @ManyToOne // Muchos pedidos pueden ser de un solo usuario
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relación IMPORTANTE: Un pedido tiene una lista de detalles
    // CascadeType.ALL: Si guardo el Pedido, se guardan sus detalles automáticamente
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id") // Esto crea la clave foránea en la tabla detalles
    private List<DetallePedido> detalles;
}