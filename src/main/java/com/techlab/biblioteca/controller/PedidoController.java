package com.techlab.biblioteca.controller;

import com.techlab.biblioteca.dto.CrearPedidoDTO;
import com.techlab.biblioteca.model.Pedido;
import com.techlab.biblioteca.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody CrearPedidoDTO datosPedido) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(datosPedido);
            return ResponseEntity.ok(nuevoPedido);
        } catch (RuntimeException e) {
            // Si falla por stock o usuario no encontrado, devolvemos error 400
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> obtenerHistorial(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorUsuario(idUsuario));
    }
    // Este endpoint es para el ADMINISTRADOR(Bilbiotecario)
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodosLosPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }
    // PUT Finalizar para prestamos
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Pedido> finalizarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.finalizarPedido(id));
    }

}