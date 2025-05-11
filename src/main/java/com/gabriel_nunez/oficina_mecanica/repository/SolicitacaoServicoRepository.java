package com.gabriel_nunez.oficina_mecanica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;

public interface SolicitacaoServicoRepository extends JpaRepository<SolicitacaoServico, Long> {
    List<SolicitacaoServico> findAllByOrderByDataHoraDesc();
}
