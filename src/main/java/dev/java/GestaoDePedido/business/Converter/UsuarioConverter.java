package dev.java.GestaoDePedido.business.Converter;

import dev.java.GestaoDePedido.Infrastructure.Entity.UsuarioEntity;
import dev.java.GestaoDePedido.business.DTO.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    // Conversão de dados do DTO para Entity
    public UsuarioEntity paraUsuario(UsuarioDTO usuarioDTO) {
        return UsuarioEntity.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .dateTime(usuarioDTO.getDateTime())
                .build();
    }

    // Conversão de dados da Entity para DTO
    public UsuarioDTO paraUsuarioDTO(UsuarioEntity entity) {
        return UsuarioDTO.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha("**********") // usuário irá ver essa senha
                .dateTime(entity.getDateTime())
                .build();
    }
}
