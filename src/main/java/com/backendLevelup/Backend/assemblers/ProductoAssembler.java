package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.dtos.Producto.ProductoDTO;
import com.backendLevelup.Backend.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoAssembler {

    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO pdto = new ProductoDTO();
        pdto.setId(producto.getId());
        pdto.setNombre(producto.getNombre());
        pdto.setDescripcion(producto.getDescripcion());
        pdto.setStock(producto.getStock());
        pdto.setPrecio(producto.getPrecio());
        pdto.setCodigo(producto.getCodigo());
        pdto.setImagenUrl(producto.getImagenUrl());

        if (producto.getCategoria() != null) {
            pdto.setNombreCategoria(producto.getCategoria().getNombre());
        }
        return pdto;
    }

}
