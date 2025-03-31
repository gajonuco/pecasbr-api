package com.sistema_restful.oficina_mecanica.repository;

import com.sistema_restful.oficina_mecanica.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
