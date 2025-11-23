package com.backendLevelup.Backend.service.ComentarioServices;

import com.backendLevelup.Backend.assemblers.ComentarioAssembler;
import com.backendLevelup.Backend.dtos.Comentario.ComentarioDTO;
import com.backendLevelup.Backend.model.Comentario;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.model.Producto;
import com.backendLevelup.Backend.repository.ComentarioRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import com.backendLevelup.Backend.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

        return comentarios.stream()
                .map(comentarioAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO guardarComentario(
            Long productoId,
            ComentarioDTO nuevoComentarioDto,
            String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + email));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));

        Comentario nuevoComentario = comentarioAssembler.toEntity(nuevoComentarioDto);

        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setProducto(producto);

        Comentario comentarioGuardado = comentarioRepository.save(nuevoComentario);

        return comentarioAssembler.toDTO(comentarioGuardado);
    }
}
