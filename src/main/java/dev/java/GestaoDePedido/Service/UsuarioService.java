package dev.java.GestaoDePedido.Service;

import dev.java.GestaoDePedido.Entity.UsuarioEntity;
import dev.java.GestaoDePedido.Exceptions.ConflictException;
import dev.java.GestaoDePedido.Exceptions.ResourceNotFoundException;
import dev.java.GestaoDePedido.Repository.UsuarioRepository;
import dev.java.GestaoDePedido.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioEntity salvarUsuario(UsuarioEntity usuario) {

        // verifica se o email já existe
        boolean verificarEmailExistente = usuarioRepository.
                existsByEmail(usuario.getEmail());

        // lança o jogo de negócio para se existe ou não existe
        if (verificarEmailExistente) {
            System.out.println("Email já cadastrado.");
        } else {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        }
        // ele nao salva no banco de dados, apenas retorna os dados que foram passados.
        return usuario;
    }

    public UsuarioEntity buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("email não encontrado " + email));
    }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

}
