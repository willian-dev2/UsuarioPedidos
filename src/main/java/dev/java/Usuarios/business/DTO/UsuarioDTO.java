package dev.java.Usuarios.business.DTO;

import dev.java.Usuarios.Infrastructure.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String nome;

    @NotBlank(message = "O email de usuário é obrigatório")
    private String email;

    @NotBlank(message = "A senha de usuário é obrigatório")
    private String senha;

    @NotNull(message = "A Role do usuário é obrigatória")
    private Role role;

    private LocalDateTime dateTime;

}
