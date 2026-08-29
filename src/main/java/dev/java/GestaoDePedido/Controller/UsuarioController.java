package dev.java.GestaoDePedido.Controller;

import dev.java.GestaoDePedido.business.DTO.UsuarioDTO;
import dev.java.GestaoDePedido.Infrastructure.Entity.UsuarioEntity;
import dev.java.GestaoDePedido.business.Service.UsuarioService;
import dev.java.GestaoDePedido.Infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),usuarioDTO.getSenha())
        );

        return ResponseEntity.ok("Bearer " + jwtUtil.generateToken(authentication.getName()));

    }

    @GetMapping("/auth")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestHeader("Authorization") String token,
                                                            @RequestBody UsuarioDTO dto) {

        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

}
