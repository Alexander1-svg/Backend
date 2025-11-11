package com.backendLevelup.Backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@Table(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Usuario {

    @Id
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private LocalDate fechaNacimiento;
    private boolean tieneDescuentoDuoc = false;

}
