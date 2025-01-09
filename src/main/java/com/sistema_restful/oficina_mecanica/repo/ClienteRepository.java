package com.sistema_restful.oficina_mecanica.repo;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
}

