package dev.java.Usuarios.Infrastructure.Entity;

import dev.java.Usuarios.Infrastructure.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;
    @CreationTimestamp
    @Column(name = "data/hora")
    private LocalDateTime dateTime;
    @Column(name = "Roles")
    @Enumerated(EnumType.STRING) // persiste como uma String no banco de dados
    private Role role;

}
