package com.backendLevelup.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Carritos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoItem> items = new ArrayList<>();

    public void addItem(CarritoItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setCarrito(this);
    }

    public void removeItem(CarritoItem item) {
        items.remove(item);
        item.setCarrito(null);
    }

    private Boolean enabled;

    @Embedded
    private Audit audit = new Audit();

    @PrePersist
    public void prePersist() {this.enabled = true;}

    @Override
    public String toString() {
        return "Carrito{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrito carrito = (Carrito) o;
        return id.equals(carrito.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
