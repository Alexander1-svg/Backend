package com.backendLevelup.Backend.service.ProductoServices;

import com.backendLevelup.Backend.assemblers.CategoriaAssembler;
import com.backendLevelup.Backend.dtos.Producto.CategoriaDTO;
import com.backendLevelup.Backend.model.Categoria;
import com.backendLevelup.Backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final  CategoriaRepository categoriaRepository;
    private final CategoriaAssembler categoriaAssembler;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaAssembler categoriaAssembler) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaAssembler = categoriaAssembler;
    }


    @Override
    public List<CategoriaDTO> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();

        return categorias.stream()
                .map(categoriaAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return categoriaAssembler.toDTO(categoria);
    }
}
