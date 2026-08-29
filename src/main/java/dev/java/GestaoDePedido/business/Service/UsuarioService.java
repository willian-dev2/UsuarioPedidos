package dev.java.GestaoDePedido.business.Service;

import dev.java.GestaoDePedido.Exceptions.ConflictException;
import dev.java.GestaoDePedido.Infrastructure.Entity.UsuarioEntity;
import dev.java.GestaoDePedido.Exceptions.ResourceNotFoundException;
import dev.java.GestaoDePedido.Infrastructure.repository.UsuarioRepository;
import dev.java.GestaoDePedido.business.Converter.UsuarioConverter;
import dev.java.GestaoDePedido.business.DTO.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        // verifica através do método se o email já existe
        emailExist(usuarioDTO.getEmail());
        // criptografia da nossa senha através do passwordEnconder
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        // Faz a conversão DTO para a entity
        UsuarioEntity usuario = usuarioConverter.paraUsuario(usuarioDTO);
        // salva usuario o objeto entity no banco de dados e devolve para o usuario o DTO
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }


    // inicio da regra de negócio para verificar se o email passado já existe no DB
    public void emailExist(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    // esse metodo retornar um boolean "true" se o metodo encontrar um usuario no Db e "false" se não encontrar
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }


    // Método para buscar no banco de dados nosso usuario através do email
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                    .orElseThrow(
                            ()-> new ResourceNotFoundException("Email não encotrado " + email)
                    )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + e.getCause());
        }
    }


    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

}
