package com.backendLevelup.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@ToString
@Table(name="Productos")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Producto {

    @Id
    private Long id;
    private String name;
    private String description;
    private Double price;

}
