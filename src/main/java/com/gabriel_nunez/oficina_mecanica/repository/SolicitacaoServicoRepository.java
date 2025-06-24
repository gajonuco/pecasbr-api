/*package com.gabriel_nunez.oficina_mecanica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel_nunez.oficina_mecanica.model.OfertaServico;
import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;


public interface SolicitacaoServicoRepository extends JpaRepository<SolicitacaoServico, Long> {
    @EntityGraph(attributePaths = {"cliente", "tipoServico"})
List<SolicitacaoServico> findAllByOrderByDataHoraDesc();
List<OfertaServico> findByTipoServicoId(Long tipoServicoId);


}*/