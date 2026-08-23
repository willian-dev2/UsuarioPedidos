package dev.java.GestaoDePedido.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UsuarioController {

    @GetMapping("/auth")
    public String usuario(){
        return "Apenas um teste da nossa controller.";
    }

}
