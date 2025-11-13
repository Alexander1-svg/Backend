package com.backendLevelup.Backend.assemblers;

import com.backendLevelup.Backend.dtos.Producto.CategoriaDTO;
import com.backendLevelup.Backend.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaAssembler {

    public CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();

        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());

        return dto;
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();

        categoria.setNombre(categoriaDTO.getNombre());

        return categoria;
    }
}
