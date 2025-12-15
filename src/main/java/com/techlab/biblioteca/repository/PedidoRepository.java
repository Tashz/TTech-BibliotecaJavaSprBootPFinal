package com.techlab.biblioteca.repository;

import com.techlab.biblioteca.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Metodo para buscar pedidos de un usuario específico
    List<Pedido> findByUsuarioId(Long usuarioId);
}