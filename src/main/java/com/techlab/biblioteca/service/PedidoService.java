package com.techlab.biblioteca.service;

import com.techlab.biblioteca.dto.CrearPedidoDTO;
import com.techlab.biblioteca.exception.RecursoNoEncontradoException;
import com.techlab.biblioteca.exception.StockInsuficienteException;
import com.techlab.biblioteca.model.*;
import com.techlab.biblioteca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private LibroService libroService;

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    // --- NUEVO: MÉTODO PARA DEVOLVER LIBROS (FINALIZAR PRÉSTAMO) ---
    @Transactional
    public Pedido finalizarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));

        if (!"EN CURSO".equals(pedido.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden finalizar pedidos que están EN CURSO.");
        }

        // Lógica de Devolución de Stock (Solo para lo que fue ALQUILER)
        for (DetallePedido detalle : pedido.getDetalles()) {
            if ("ALQUILER".equalsIgnoreCase(detalle.getTipo())) {
                Libro libro = detalle.getLibro();
                // Devolvemos el libro a la estantería
                libro.setStock(libro.getStock() + detalle.getCantidad());
                libroRepository.save(libro);
            }
        }

        pedido.setEstado("COMPLETADO");
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido crearPedido(CrearPedidoDTO datos) {
        Usuario usuario;

        // 1. Lógica de Usuario (Web con ID o Cliente Mostrador/Guest)
        if (datos.getUsuarioId() != null) {
            usuario = usuarioRepository.findById(datos.getUsuarioId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario ID no encontrado"));
        }
        else if (datos.getClienteMostrador() != null) {
            CrearPedidoDTO.DatosClienteMostrador info = datos.getClienteMostrador();

            // Validamos campos obligatorios para todos (Web Guest y Mostrador)
            if (info.getDni() == null || info.getDni().isEmpty() || info.getNombre() == null || info.getApellido() == null) {
                throw new IllegalArgumentException("Nombre, Apellido y DNI son obligatorios para procesar la orden.");
            }

            usuario = usuarioRepository.findByDni(info.getDni()).orElse(null);
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setNombre(info.getNombre());
                usuario.setApellido(info.getApellido());
                usuario.setDni(info.getDni());
                usuario.setTelefono(info.getTelefono());
                usuario.setEmail(info.getEmail() != null && !info.getEmail().isEmpty() ? info.getEmail() : info.getDni() + "@guest.com");
                usuario.setPassword("guest123");
                usuario = usuarioRepository.save(usuario);
            }
        } else {
            throw new IllegalArgumentException("Faltan datos del cliente.");
        }

        // 2. Determinar estado inicial
        // Si hay al menos un ALQUILER, el pedido queda "EN CURSO" (el libro está fuera).
        // Si todo es VENTA, el pedido queda "COMPLETADO" (el libro se fue para siempre).
        boolean hayAlquileres = datos.getItems().stream()
                .anyMatch(item -> "ALQUILER".equalsIgnoreCase(item.getTipo()));

        String estadoInicial = hayAlquileres ? "EN CURSO" : "COMPLETADO";

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setUsuario(usuario);
        pedido.setEstado(estadoInicial);
        pedido.setDetalles(new ArrayList<>());

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (CrearPedidoDTO.ItemPedidoDTO item : datos.getItems()) {
            Libro libro = libroRepository.findById(item.getLibroId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado ID: " + item.getLibroId()));

            BigDecimal precioAplicado;

            if ("VENTA".equalsIgnoreCase(item.getTipo())) {
                if (!libroService.puedeVenderse(libro, item.getCantidad())) {
                    throw new StockInsuficienteException("Stock insuficiente para VENTA de: '" + libro.getTitulo() + "'. Reserva activa.");
                }
                precioAplicado = libro.getPrecioVenta();
            } else if ("ALQUILER".equalsIgnoreCase(item.getTipo())) {
                if (libro.getStock() < item.getCantidad()) {
                    throw new StockInsuficienteException("No hay copias para ALQUILAR de: " + libro.getTitulo());
                }
                precioAplicado = libro.getCostoAlquiler();
            } else {
                throw new IllegalArgumentException("Tipo inválido: " + item.getTipo());
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setLibro(libro);
            detalle.setCantidad(item.getCantidad());
            detalle.setTipo(item.getTipo().toUpperCase());
            detalle.setPrecioUnitario(precioAplicado);

            BigDecimal subtotal = precioAplicado.multiply(new BigDecimal(item.getCantidad()));
            detalle.setSubtotal(subtotal);

            pedido.getDetalles().add(detalle);
            totalPedido = totalPedido.add(subtotal);

            // Siempre descontamos al sacar el libro
            libroService.descontarStock(libro, item.getCantidad());
        }

        pedido.setTotal(totalPedido);
        return pedidoRepository.save(pedido);
    }
}