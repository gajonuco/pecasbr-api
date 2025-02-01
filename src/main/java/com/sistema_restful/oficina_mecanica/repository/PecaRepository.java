package com.sistema_restful.oficina_mecanica.repository;

import com.sistema_restful.oficina_mecanica.model.Peca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {
    Page<Peca> findByUserId(UUID userId, Pageable pageable);
}

