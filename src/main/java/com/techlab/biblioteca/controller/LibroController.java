package com.techlab.biblioteca.controller;

import com.techlab.biblioteca.model.Libro;
import com.techlab.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;


    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros(@RequestParam(required = false) String buscar) {
        return ResponseEntity.ok(libroService.buscarLibros(buscar));
    }

    // 4. Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        // Esto lanzará 404 automáticamente si el ID no existe
        Libro libroExistente = libroService.obtenerPorId(id);

        libroExistente.setTitulo(libroDetalles.getTitulo());
        libroExistente.setStock(libroDetalles.getStock());
        libroExistente.setPrecioVenta(libroDetalles.getPrecioVenta());
        libroExistente.setCostoAlquiler(libroDetalles.getCostoAlquiler());
        libroExistente.setAutor(libroDetalles.getAutor());
        libroExistente.setCategoria(libroDetalles.getCategoria());
        libroExistente.setImagenUrl(libroDetalles.getImagenUrl());


        return ResponseEntity.ok(libroService.guardarLibro(libroExistente));
    }

    // 5. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        // Verificamos que exista antes de borrar (lanza 404 si no existe)
        libroService.obtenerPorId(id);

        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}