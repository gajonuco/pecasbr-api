package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.model.OrcamentosServicoMecanico;
import com.gabriel_nunez.oficina_mecanica.model.TipoServicoMecanico;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrcamentosServicoMecanicoRepository extends JpaRepository<OrcamentosServicoMecanico, Long> {
    
    List<OrcamentosServicoMecanico> findByTipoServicoId(TipoServicoMecanico tipoServicoId);

    List<OrcamentosServicoMecanico> findByTipoServicoIdAndAtivoTrue(Long tipoServicoId); // <-- Novo método
}
