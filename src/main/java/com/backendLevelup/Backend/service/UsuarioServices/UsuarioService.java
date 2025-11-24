package com.backendLevelup.Backend.service.UsuarioServices;

import com.backendLevelup.Backend.dtos.Usuario.LoginDTO;
import com.backendLevelup.Backend.dtos.Usuario.RegistroUsuarioDTO;
import com.backendLevelup.Backend.dtos.Usuario.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UsuarioService {

    UsuarioDTO createUsuario(RegistroUsuarioDTO dto);

    UsuarioDTO login(LoginDTO dto);

    List<UsuarioDTO> findAll();
}
