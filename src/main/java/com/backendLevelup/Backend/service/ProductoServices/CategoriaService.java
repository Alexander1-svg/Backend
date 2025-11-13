package com.backendLevelup.Backend.service.ProductoServices;

import com.backendLevelup.Backend.dtos.Producto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> getAllCategorias();
    CategoriaDTO getCategoriaById(Long id);

}
