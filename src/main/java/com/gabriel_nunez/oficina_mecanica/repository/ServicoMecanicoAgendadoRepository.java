package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.model.ServicoMecanicoAgendado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoMecanicoAgendadoRepository extends JpaRepository<ServicoMecanicoAgendado, Long> {
}
