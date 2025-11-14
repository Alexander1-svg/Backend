package com.backendLevelup.Backend.dtos.Usuario;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private boolean tieneDescuentoDuoc;
    private String rol;

}
