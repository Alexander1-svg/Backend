package com.backendLevelup.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CarritoItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad; // Cantidad del producto

    // 1. RELACIÓN INVERSA CON CARRIO (Muchos a Uno)
    // Muchos CarritoItem pueden pertenecer a UN Carrito.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito; // <-- Este es el campo referenciado por 'mappedBy' en Carrito

    // 2. RELACIÓN CON PRODUCTO (Muchos a Uno)
    // Muchos CarritoItem pueden referirse a UN Producto.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
