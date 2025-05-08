package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteUsuarioRepository extends JpaRepository<ClienteUsuario, Long> {
    Optional<ClienteUsuario> findByLogin(String login);
}
