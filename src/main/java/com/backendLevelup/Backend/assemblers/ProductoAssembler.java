package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.dtos.Producto.ProductoDTO;
import com.backendLevelup.Backend.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoAssembler {

    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO pdto = new ProductoDTO();
        pdto.setId(producto.getId());
        pdto.setNombre(producto.getName());
        pdto.setDescripcion(producto.getDescription());
        pdto.setPrecio(producto.getPrice());

        if (producto.getCategoria() != null) {
            pdto.setNombreCategoria(producto.getCategoria().getNombre());
        }
        return pdto;
    }

}
