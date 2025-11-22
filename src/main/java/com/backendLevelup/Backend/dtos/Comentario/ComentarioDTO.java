package com.backendLevelup.Backend.dtos.Comentario;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Provee getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    private Long id; // Útil para mostrarlo
    private String comentario; // El texto del comentario (el único campo que viene del cliente)

    // Campos para mostrar al cliente (no vienen en el POST, sino en el GET):
    private String nombreUsuario; // Para mostrar quién lo escribió
    private Long productoId;
    private String fechaCreacion;
}
