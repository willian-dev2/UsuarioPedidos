package dev.java.GestaoDePedido.Repository;

import dev.java.GestaoDePedido.Entity.UsuarioEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UsuarioEntity> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
