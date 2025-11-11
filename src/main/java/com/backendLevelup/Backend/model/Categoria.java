package com.backendLevelup.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categorias")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Categoria {

    @Id
    private Long id;
    private String nombre;
    private List<Producto> productos =  new ArrayList<>();

}
