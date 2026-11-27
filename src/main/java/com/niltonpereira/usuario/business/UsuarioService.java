package com.niltonpereira.usuario.business;

import com.niltonpereira.usuario.business.converter.UsuarioConverter;
import com.niltonpereira.usuario.business.dto.UsuarioDTO;
import com.niltonpereira.usuario.infrastructure.entity.Usuario;
import com.niltonpereira.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){

        // usuarioDTO transformou ele em uma Entidade
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        // salvou no banco de dados
        Usuario usuariosalvo = usuarioRepository.save(usuario);
        // converteu ele paraUsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuariosalvo);
    }
}
