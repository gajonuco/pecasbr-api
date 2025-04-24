package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca, Long> {
    boolean existsBySku(String sku);
    Optional<Peca> findBySku(String sku);
}
