package dev.java.Usuarios.Controller;

import dev.java.Usuarios.Infrastructure.enums.Role;
import dev.java.Usuarios.business.DTO.UsuarioDTO;
import dev.java.Usuarios.business.Service.UsuarioService;
import dev.java.Usuarios.Infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        Role role = Role.valueOf(
                authentication.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace("ROLE_", "")
        );

        return ResponseEntity.ok("Bearer " + jwtUtil.generateToken(authentication.getName(), role));

    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email,
                                                            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email,
                                                       @RequestHeader("Authorization") String token) {
        usuarioService.deletarUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestHeader("Authorization") String token,
                                                            @RequestBody UsuarioDTO dto,
                                                            @RequestParam("email") String email) {

        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto, email));
    }

}
