package com.backendLevelup.Backend.service.ProductoServices;

import com.backendLevelup.Backend.dtos.Producto.CategoriaDTO;
import com.backendLevelup.Backend.repository.CategoriaRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;

import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {

    private final  CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaDTO> getAllCategorias() {
        return List.of();
    }

    @Override
    public CategoriaDTO createCategoria(CategoriaDTO categoriaDTO) {
        return null;
    }

    @Override
    public CategoriaDTO getCategoriaById(Long id) {
        return null;
    }
}
