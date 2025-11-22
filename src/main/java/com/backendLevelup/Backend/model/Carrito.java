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

    // 1. RELACIÓN CON USUARIO (Uno a Uno)
    // Un Carrito pertenece a UN Usuario, y un Usuario tiene UN Carrito.
    // 'mappedBy' no se usa aquí. 'JoinColumn' es necesario para definir la clave foránea.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    // 2. RELACIÓN CON CARRIOITEM (Uno a Muchos)
    // Un Carrito tiene muchos CarritoItem.
    // 'mappedBy' apunta al campo 'carrito' en la entidad CarritoItem.
    // CascadeType.ALL: Si se elimina el carrito, se eliminan todos sus ítems.
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoItem> items = new ArrayList<>();

    // Opcional: Campo para almacenar la fecha de creación/última actualización
    // private LocalDateTime fechaCreacion;

    // --- Métodos de Conveniencia (Ayudan a mantener la consistencia de las relaciones) ---

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



}
