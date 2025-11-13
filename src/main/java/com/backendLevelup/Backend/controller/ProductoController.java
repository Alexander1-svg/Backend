package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Producto.ProductoDTO;
import com.backendLevelup.Backend.service.ProductoServices.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.getAllProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable String id) {
        ProductoDTO producto = productoService.getProductoById(Long.valueOf(id));
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoria(@PathVariable String nombreCategoria) {

        List<ProductoDTO> productos = productoService.getProductosByCategoria(nombreCategoria);
        return ResponseEntity.ok(productos);
    }
}
