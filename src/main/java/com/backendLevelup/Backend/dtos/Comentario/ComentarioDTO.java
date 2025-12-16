package com.backendLevelup.Backend.dtos.Comentario;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    private Long id;
    private String comentario;
    private String nombreUsuario;
    private Long productoId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime fechaCreacion;
    private boolean enabled;
}
