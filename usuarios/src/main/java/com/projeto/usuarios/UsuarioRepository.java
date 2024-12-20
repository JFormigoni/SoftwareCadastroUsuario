package com.projeto.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    long countByNivelAcesso(String nivelAcesso);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
