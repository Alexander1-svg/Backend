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

    private Boolean enabled;

    @Embedded
    private Audit audit = new Audit();

    @PrePersist
    public void prePersist() {this.enabled = true;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return id.equals(categoria.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
