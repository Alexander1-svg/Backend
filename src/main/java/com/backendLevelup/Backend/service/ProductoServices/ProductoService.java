package com.backendLevelup.Backend.service.ProductoServices;



import com.backendLevelup.Backend.dtos.Producto.ProductoDTO;

import java.util.List;


public interface ProductoService {

    List<ProductoDTO> getAllProductos();

    ProductoDTO getProductoById(Long id);

    ProductoDTO getProductosByCategorias(String nombreCategoria);
}
