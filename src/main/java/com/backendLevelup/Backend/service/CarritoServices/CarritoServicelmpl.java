package com.backendLevelup.Backend.service.CarritoServices;

import com.backendLevelup.Backend.assemblers.CarritoAssembler;
import com.backendLevelup.Backend.dtos.Carrito.CarritoDTO;
import com.backendLevelup.Backend.dtos.Carrito.CarritoItemDetalleDTO;
import com.backendLevelup.Backend.exceptions.ResourceNotFoundException;
import com.backendLevelup.Backend.model.Carrito;
import com.backendLevelup.Backend.model.CarritoItem;
import com.backendLevelup.Backend.model.Producto;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.CarritoItemRepository;
import com.backendLevelup.Backend.repository.CarritoRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CarritoServicelmpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final CarritoAssembler carritoAssembler;

    public CarritoServicelmpl(
            CarritoRepository carritoRepository,
            CarritoItemRepository carritoItemRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository,
            CarritoAssembler carritoAssembler) {

        this.carritoRepository = carritoRepository;
        this.itemRepository = carritoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.carritoAssembler = carritoAssembler;
    }

    private Usuario getUsuarioByEmail(String email) {
        // Usa findByEmail, ya que 'email' es el identificador de login en tu modelo Usuario
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    // Método clave: Busca el carrito del usuario, o crea uno nuevo si no existe.
    private Carrito getOrCreateCarritoEntity(Usuario usuario) {
        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    // Spring Data JPA lo guarda automáticamente si es una entidad persistente
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    // --- Implementación de la Interfaz CarritoService ---

    @Override
    public CarritoDTO getOrCreateCarrito(String emailUsuario) {
        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

        // Mapea la entidad a DTO y calcula los totales
        return carritoAssembler.toDTO(carrito);
    }

    @Override
    public CarritoDTO agregarProductoAlCarrito(String emailUsuario, CarritoItemDetalleDTO itemDto) {
        if (itemDto.getCantidad() == null || itemDto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad a agregar debe ser mayor a cero.");
        }

        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

        Producto producto = productoRepository.findById(itemDto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + itemDto.getProductoId()));

        // 1. Busca si el ítem (ProductoId) ya existe en este carrito
        Optional<CarritoItem> existingItemOpt = itemRepository.findByCarritoIdAndProductoId(carrito.getId(), producto.getId());

        if (existingItemOpt.isPresent()) {
            // Caso 1: El ítem ya existe, incrementamos la cantidad
            CarritoItem existingItem = existingItemOpt.get();
            existingItem.setCantidad(existingItem.getCantidad() + itemDto.getCantidad());
            itemRepository.save(existingItem);
        } else {
            // Caso 2: El ítem es nuevo, creamos un CarritoItem
            CarritoItem newItem = new CarritoItem();
            newItem.setCarrito(carrito);
            newItem.setProducto(producto);
            newItem.setCantidad(itemDto.getCantidad());

            // Relaciona el ítem con el carrito (y lo guarda, debido a CascadeType.ALL en Carrito)
            // Si no usaste addItem() en Carrito, usa itemRepository.save(newItem);
            carrito.getItems().add(newItem);
        }

        // Devuelve la vista actualizada
        return carritoAssembler.toDTO(carrito);
    }

    @Override
    public CarritoDTO removerProductoDelCarrito(String emailUsuario, Long itemId) {
        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

        CarritoItem itemToRemove = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem de carrito no encontrado con ID: " + itemId));

        // Verificación de seguridad: Asegura que el ítem pertenece al carrito del usuario
        if (!itemToRemove.getCarrito().getId().equals(carrito.getId())) {
            throw new SecurityException("El ítem no pertenece al carrito del usuario.");
        }

        // Elimina el ítem (orphanRemoval=true en Carrito se encargará de la colección)
        itemRepository.delete(itemToRemove);

        // Devuelve el carrito actualizado después de la eliminación
        return carritoAssembler.toDTO(carrito);
    }

    @Override
    public CarritoDTO actualizarCantidadItem(String emailUsuario, Long itemId, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            // Si la cantidad es cero o menos, llama al método de eliminación
            return removerProductoDelCarrito(emailUsuario, itemId);
        }

        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

        CarritoItem itemToUpdate = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem de carrito no encontrado con ID: " + itemId));

        // Verificación de seguridad
        if (!itemToUpdate.getCarrito().getId().equals(carrito.getId())) {
            throw new SecurityException("El ítem no pertenece al carrito del usuario.");
        }

        itemToUpdate.setCantidad(nuevaCantidad);
        itemRepository.save(itemToUpdate); // Guarda el cambio de cantidad

        // Devuelve el carrito actualizado
        return carritoAssembler.toDTO(carrito);
    }

}
