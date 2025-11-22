package com.backendLevelup.Backend.service.ComentarioServices;

import com.backendLevelup.Backend.assemblers.ComentarioAssembler;
import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import com.backendLevelup.Backend.model.Comentario;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.model.Producto;
import com.backendLevelup.Backend.repository.ComentarioRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import com.backendLevelup.Backend.exceptions.ResourceNotFoundException; // Asume que tienes esta excepción
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioServicelmpl implements ComentarioService{

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ComentarioAssembler comentarioAssembler;

    public ComentarioServicelmpl(
            ComentarioRepository comentarioRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository,
            ComentarioAssembler comentarioAssembler){

        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.comentarioAssembler = comentarioAssembler;
    }

    @Override
    public List<ComentarioDTO> getComentariosByProducto(Long productoId) {
        List<Comentario> comentarios = comentarioRepository.findByProductoId(productoId);

        // Mapea la lista de entidades a una lista de DTOs usando el Assembler
        return comentarios.stream()
                .map(comentarioAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO guardarComentario(
            Long productoId,
            ComentarioDTO nuevoComentarioDto,
            String email) {

        // 1. **Buscar el Usuario**
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + email));

        // 2. **Buscar el Producto**
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));

        // 3. **Convertir DTO a Entidad Comentario** (Solo con el texto del comentario)
        Comentario nuevoComentario = comentarioAssembler.toEntity(nuevoComentarioDto);

        // 4. **Asignar las referencias**
        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setProducto(producto);
        // Opcional: setea la fecha de creación si no se hace en la entidad

        // 5. **Guardar en la BD**
        Comentario comentarioGuardado = comentarioRepository.save(nuevoComentario);

        // 6. **Devolver el DTO**
        return comentarioAssembler.toDTO(comentarioGuardado);
    }
}
