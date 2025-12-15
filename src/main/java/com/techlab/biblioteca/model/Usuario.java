package com.techlab.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido; // Nuevo

    @Column(unique = true)
    private String email;

    private String password;

    private String dni;      // Nuevo
    private String telefono; // Nuevo
}