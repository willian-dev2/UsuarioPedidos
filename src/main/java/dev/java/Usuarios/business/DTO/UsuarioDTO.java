package dev.java.Usuarios.business.DTO;

import dev.java.Usuarios.Infrastructure.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private Role role;
    private LocalDateTime dateTime;

}
