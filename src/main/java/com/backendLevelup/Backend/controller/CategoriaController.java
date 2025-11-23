package com.backendLevelup.Backend.controller;

import com.backendLevelup.Backend.dtos.Producto.CategoriaDTO;
import com.backendLevelup.Backend.service.ProductoServices.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Endpoint para obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        List<CategoriaDTO> categorias = categoriaService.getAllCategorias();

        return ResponseEntity.ok(categorias);
    }

    // Endpoint para obtener una categoría por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable String id) {
        CategoriaDTO categoria = categoriaService.getCategoriaById(Long.valueOf(id));
        return ResponseEntity.ok(categoria);
    }

    // Endpoint para obtener una categoría por su nombre
    @GetMapping("/nombre/{nombreCategoria}")
    public ResponseEntity<CategoriaDTO> getCategoriaByNombre(@PathVariable String nombreCategoria) {
        CategoriaDTO categoria = categoriaService.getCategoriaByNombre(nombreCategoria);
        return ResponseEntity.ok(categoria);
    }


}
