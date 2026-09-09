package dev.java.Usuarios.business.Converter;

import dev.java.Usuarios.Infrastructure.Entity.UsuarioEntity;
import dev.java.Usuarios.business.DTO.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    // Conversão de dados do DTO para Entity
    public UsuarioEntity paraUsuario(UsuarioDTO usuarioDTO) {
        return UsuarioEntity.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .role(usuarioDTO.getRole())
                .dateTime(usuarioDTO.getDateTime())
                .build();
    }

    // Conversão de dados da Entity para DTO
    public UsuarioDTO paraUsuarioDTO(UsuarioEntity entity) {
        return UsuarioDTO.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha("**********") // usuário irá ver essa senha
                .role(entity.getRole())
                .dateTime(entity.getDateTime())
                .build();
    }


    public UsuarioEntity updateUsuario(UsuarioDTO dto, UsuarioEntity entity) {
        return UsuarioEntity.builder()
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .id(entity.getId())
                .role(entity.getRole())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .build();
    }

}
