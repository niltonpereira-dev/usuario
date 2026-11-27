package com.niltonpereira.usuario.business;

import com.niltonpereira.usuario.business.converter.UsuarioConverter;
import com.niltonpereira.usuario.business.dto.UsuarioDTO;
import com.niltonpereira.usuario.infrastructure.entity.Usuario;
import com.niltonpereira.usuario.infrastructure.exceptions.ConflictException;
import com.niltonpereira.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.niltonpereira.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder  passwordEncoder ;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){

        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        // usuarioDTO transformou ele em uma Entidade
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        // salvou no banco de dados
        Usuario usuariosalvo = usuarioRepository.save(usuario);
        // converteu ele paraUsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuariosalvo);
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificarEmailExistente(email);
            if (existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }


    public boolean verificarEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deleteUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
