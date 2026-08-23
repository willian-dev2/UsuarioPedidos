package dev.java.GestaoDePedido.Controller;

import dev.java.GestaoDePedido.Entity.UsuarioEntity;
import dev.java.GestaoDePedido.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping()
    public String usuario(){
        return "Apenas um teste da nossa controller.";
    }

    @PostMapping
    public ResponseEntity<UsuarioEntity> salvarUsuario(@RequestBody UsuarioEntity usuario) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }

}
