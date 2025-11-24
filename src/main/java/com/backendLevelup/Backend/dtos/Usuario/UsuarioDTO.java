package com.backendLevelup.Backend.dtos.Usuario;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private boolean tieneDescuentoDuoc;
    private List<String> roles;
    private boolean enabled;

}
