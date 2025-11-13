package com.backendLevelup.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categorias")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos =  new ArrayList<>();

}
