package dev.java.Usuarios.business.Service;

import dev.java.Usuarios.Infrastructure.Exceptions.ConflictException;
import dev.java.Usuarios.Infrastructure.Entity.UsuarioEntity;
import dev.java.Usuarios.Infrastructure.Exceptions.ResourceNotFoundException;
import dev.java.Usuarios.Infrastructure.repository.UsuarioRepository;
import dev.java.Usuarios.Infrastructure.security.JwtUtil;
import dev.java.Usuarios.business.Converter.UsuarioConverter;
import dev.java.Usuarios.business.DTO.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioConverter usuarioConverter;
    private final JwtUtil jwtUtil;


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

    public UsuarioEntity temEmailDb (String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Email não encontrado " + email));
    }


    // Método para buscar no banco de dados nosso usuario através do email
    public UsuarioDTO buscarUsuarioPorEmail(String email, String token) {
        // faz a busca no banco de dados
        UsuarioEntity usuarioEntity = temEmailDb(email);
        // compara se o email passado é igual ao do token ou se tem uma Role admin
        compararEmail(email, token);
        // retorna convertendo para nosso dto a entity
        return usuarioConverter.paraUsuarioDTO(usuarioEntity);
    }


    public void deletarUsuarioPorEmail(String email, String token) {
        temEmailDb(email);
        compararEmail(email, token);
        usuarioRepository.deleteByEmail(email);
    }

    public void compararEmail(String email, String token) {
        try {
            // buscar email do usuario através do token
            String emailToken = jwtUtil.extractUsername((token.substring(7)));
            if (!emailToken.equals(email)) {
                boolean role = compararRole(token);
                if (!role) {
                    throw new ConflictException("É necessario um role ADMIN para acessar outro email " + emailToken);
                }
            }
        } catch (
                ResourceNotFoundException e) {
            throw new ResourceNotFoundException("É necessario um role ADMIN para acessar outro email " + e.getCause());
        }
    }

    public boolean compararRole(String token) {
        // busca o ROLE do usuario através do token
        String role = jwtUtil.extractRole(token.substring(7));

        // método boolean ja direto no return, se for igual retorna true, se não, retorna false
        return role.equals("ADMIN");
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto, String email) {

        // busca email no banco de dados
        UsuarioEntity usuarioEntity = temEmailDb(email);

        // Aqui chamaremos o método para verificar se é o admin que esta fazendo essa operacao ou o proprio dono do email
        compararEmail(email, token);

        // Se for passada uma nova senha, será feita uma nova criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);


        // mescla os dados recebidos na requisição DTO com os dados do banco de dados
        UsuarioEntity usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        // salva dados do usuario convertido e depois pega o retorno e converte para UsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

}
