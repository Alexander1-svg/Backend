package com.backendLevelup.Backend.service.ProductoServices;

import com.backendLevelup.Backend.assemblers.ProductoAssembler;
import com.backendLevelup.Backend.dtos.Producto.ProductoDTO;
import com.backendLevelup.Backend.model.Producto;
import com.backendLevelup.Backend.repository.CategoriaRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoAssembler productoAssembler;


    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProductoAssembler productoAssembler) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoAssembler = productoAssembler;
    }

    @Override
    public List<ProductoDTO> getAllProductos(){
        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .map(productoAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoAssembler.toDTO(producto);
    }

    @Override
    public List<ProductoDTO> getProductosByCategoria(String nombreCategoria) {
        if (!categoriaRepository.existsByNombre(nombreCategoria)){
            throw new RuntimeException("Categoria no encontrada " + nombreCategoria);
        }

        List<Producto> productos = productoRepository.findByCategoriaNombre(nombreCategoria);

        return productos.stream()
                .map(productoAssembler::toDTO)
                .collect(Collectors.toList());
    }
}
