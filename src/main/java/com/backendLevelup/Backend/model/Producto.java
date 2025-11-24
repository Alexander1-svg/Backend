package com.backendLevelup.Backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@ToString
@Table(name="Productos")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int precio;

    @Column(nullable = false, unique = true)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "imagen_url")
    private String imagenUrl;

    private Boolean enabled;

    @Embedded
    private Audit audit = new Audit();

    @PrePersist
    public void prePersist() {this.enabled = true;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id) && Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode(){return Objects.hash(id, nombre);}

}
