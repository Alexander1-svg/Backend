package com.backendLevelup.Backend.dtos.Usuario;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {
    private String nombre;
    private String email;
    private String password;
    private LocalDate fechaNacimiento;

}
