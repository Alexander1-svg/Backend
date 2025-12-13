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
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    // Método clave: Busca el carrito del usuario, o crea uno nuevo si no existe.
    private Carrito getOrCreateCarritoEntity(Usuario usuario) {
        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    @Override
    public CarritoDTO getOrCreateCarrito(String emailUsuario) {
        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

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

        Optional<CarritoItem> existingItemOpt = itemRepository.findByCarritoIdAndProductoId(carrito.getId(), producto.getId());

        if (existingItemOpt.isPresent()) {
            CarritoItem existingItem = existingItemOpt.get();
            existingItem.setCantidad(existingItem.getCantidad() + itemDto.getCantidad());
            itemRepository.save(existingItem);
        } else {
            CarritoItem newItem = new CarritoItem();
            newItem.setProducto(producto);
            newItem.setCantidad(itemDto.getCantidad());

            carrito.addItem(newItem);

            itemRepository.save(newItem);
        }

        return carritoAssembler.toDTO(carrito);
    }

    @Override
    public CarritoDTO removerProductoDelCarrito(String emailUsuario, Long itemId) {
        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

        CarritoItem itemToRemove = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem de carrito no encontrado con ID: " + itemId));

        if (!itemToRemove.getCarrito().getId().equals(carrito.getId())) {
            throw new SecurityException("El ítem no pertenece al carrito del usuario.");
        }

        carrito.removeItem(itemToRemove);

        Carrito carritoActualizado = carritoRepository.save(carrito);

        return carritoAssembler.toDTO(carritoActualizado);
    }

    @Override
    public CarritoDTO actualizarCantidadItem(String emailUsuario, Long itemId, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return removerProductoDelCarrito(emailUsuario, itemId);
        }

        Usuario usuario = getUsuarioByEmail(emailUsuario);
        Carrito carrito = getOrCreateCarritoEntity(usuario);

        CarritoItem itemToUpdate = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem de carrito no encontrado con ID: " + itemId));

        if (!itemToUpdate.getCarrito().getId().equals(carrito.getId())) {
            throw new SecurityException("El ítem no pertenece al carrito del usuario.");
        }

        itemToUpdate.setCantidad(nuevaCantidad);

        itemRepository.save(itemToUpdate);

        return carritoAssembler.toDTO(carrito);
    }

}
