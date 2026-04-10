/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.PecaVariacaoDAO
 *  com.gabriel_nunez.oficina_mecanica.model.PecaVariacao
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Modifying
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.query.Param
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.model.PecaVariacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PecaVariacaoDAO
extends JpaRepository<PecaVariacao, Integer> {
    public List<PecaVariacao> findByPecaIdOrderByCorAscTamanhoAsc(Integer var1);

    @Query("SELECT COUNT(DISTINCT v.cor) FROM PecaVariacao v WHERE v.peca.id = :idPeca")
    public long countCoresDistintas(@Param("idPeca") Integer var1);

    @Query("SELECT COUNT(DISTINCT v.tamanho) FROM PecaVariacao v WHERE v.peca.id = :idPeca")
    public long countTamanhosDistintos(@Param("idPeca") Integer var1);

    public Optional<PecaVariacao> findByPecaIdAndCorAndTamanho(Integer var1, String var2, String var3);

    @Modifying
    @Query("DELETE FROM PecaVariacao v WHERE v.peca.id = :idPeca")
    public void deleteByPecaId(@Param("idPeca") Integer var1);

    @Modifying
    @Query("UPDATE ItemPedido ip SET ip.variacao = null WHERE ip.variacao.id IN (SELECT v.id FROM PecaVariacao v WHERE v.peca.id = :idPeca)")
    public void desvinculaItensPedidoDaPeca(@Param("idPeca") Integer var1);
}

