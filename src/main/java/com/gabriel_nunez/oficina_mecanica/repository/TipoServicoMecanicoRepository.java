package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.model.TipoServicoMecanico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoServicoMecanicoRepository extends JpaRepository<TipoServicoMecanico, Long> {
    Optional<TipoServicoMecanico> findByNome(String nome);
}
