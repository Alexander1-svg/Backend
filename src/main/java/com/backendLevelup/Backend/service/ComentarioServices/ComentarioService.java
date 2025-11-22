package com.backendLevelup.Backend.service.ComentarioServices;

import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;

import java.util.List;

public interface ComentarioService {

    List<ComentarioDTO> getComentariosByProducto(Long productoId);

    ComentarioDTO guardarComentario(Long productoId, ComentarioDTO nuevoComentarioDto, String username);
}
