package com.techlab.biblioteca.dto;

import lombok.Data;
import java.util.List;

@Data
public class CrearPedidoDTO {
    private Long usuarioId; // Opcional (para usuarios web)
    private DatosClienteMostrador clienteMostrador; // Opcional (para venta física)

    private List<ItemPedidoDTO> items;

    @Data
    public static class ItemPedidoDTO {
        private Long libroId;
        private Integer cantidad;
        private String tipo;
    }

    @Data
    public static class DatosClienteMostrador {
        private String nombre;
        private String apellido;
        private String dni;
        private String telefono;
        private String email;
    }
}