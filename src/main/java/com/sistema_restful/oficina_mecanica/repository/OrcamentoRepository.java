package com.sistema_restful.oficina_mecanica.repo;

import com.sistema_restful.oficina_mecanica.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
}
