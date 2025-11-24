package com.backendLevelup.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "Comentarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comentario {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    private String comentario;

    private Boolean enabled;

    @Embedded
    private Audit audit = new Audit();

    @PrePersist
    public void prePersist() {this.enabled = true;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario comentario = (Comentario) o;
        return Objects.equals(id, comentario.id);
    }

    @Override
    public int hashCode(){return Objects.hash(id);}
}
