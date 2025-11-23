package com.backendLevelup.Backend.dtos.Comentario;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    private Long id;
    private String comentario;
    private String nombreUsuario;
    private Long productoId;
    private String fechaCreacion;
}
