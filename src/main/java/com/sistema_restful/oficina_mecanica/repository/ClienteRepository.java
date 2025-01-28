package com.sistema_restful.oficina_mecanica.repository;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.id IN :ids")
    List<Cliente> findClientesByIds(@Param("ids") List<Long> ids);

    Page<Cliente> findByAutorUserId(UUID autorId, Pageable pageable);
}




