package com.techlab.biblioteca.service;

import com.techlab.biblioteca.exception.RecursoNoEncontradoException;
import com.techlab.biblioteca.model.Libro;
import com.techlab.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.techlab.biblioteca.exception.OperacionNoPermitidaException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + id));
    }

    // --- MÉTODOS  ---

    // 1. Validar si se puede vender respetando la reserva de 5
    public boolean puedeVenderse(Libro libro, int cantidadAComprar) {
        int stockRestante = libro.getStock() - cantidadAComprar;
        // Si sobran 5 o más, permitimos la venta
        return stockRestante >= 5;
    }

    // 2. Descontar el stock real después de la venta
    public void descontarStock(Libro libro, int cantidad) {
        libro.setStock(libro.getStock() - cantidad);
        libroRepository.save(libro);
    }

    // Eliminar libro
    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se puede eliminar. Libro no encontrado ID: " + id);
        }

        try {
            libroRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Esto ocurre si el libro está en la tabla detalles_pedido
            throw new OperacionNoPermitidaException("No se puede eliminar este libro porque tiene pedidos/préstamos asociados. Intenta desactivarlo o cambiar el stock a 0.");
        }
    }
    public List<Libro> buscarLibros(String busqueda) {
        if (busqueda == null || busqueda.isEmpty()) {
            return libroRepository.findAll();
        }
        return libroRepository.findByTituloContainingIgnoreCase(busqueda);
    }

}