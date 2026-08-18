/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.PecaDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  org.springframework.data.domain.Page
 *  org.springframework.data.domain.Pageable
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.query.Param
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PecaDAO
extends JpaRepository<Peca, Integer> {
    public Page<Peca> findAllByDisponivel(int var1, Pageable var2);

    public Optional<Peca> findById(int var1);

    public ArrayList<Peca> findAllByCategoriaPecaAndDisponivel(CategoriaPeca var1, int var2);

    public ArrayList<Peca> findAllByCategoriaPeca(CategoriaPeca var1);

    public ArrayList<Peca> findByNomeContainingOrDetalheContaining(String var1, String var2);

    public Page<Peca> findAllByDestaqueAndDisponivel(int var1, int var2, Pageable var3);

    public Page<Peca> findAllByDisponivelAndNomeContainingOrDisponivelAndDetalheContaining(int var1, String var2, int var3, String var4, Pageable var5);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO( p.id, p.nome, p.linkFoto, p.preco, p.precoPromo, SUM(ip.qtdtItem), SUM(ip.precoTotal)) FROM ItemPedido ip JOIN ip.peca p JOIN ip.pedido ped WHERE ped.status = 1 AND ped.dataPedido BETWEEN :dataInicio AND :dataFim GROUP BY p.id, p.nome, p.linkFoto, p.preco, p.precoPromo ORDER BY SUM(ip.qtdtItem) DESC ")
    public ArrayList<ProdutoMaisPedidoDTO> findProdutosMaisPedidosPorQuantidade(@Param("dataInicio") LocalDate var1, @Param("dataFim") LocalDate var2, Pageable var3);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO(p.id, p.nome, p.linkFoto, p.preco, p.precoPromo, SUM(ip.qtdtItem), SUM(ip.precoTotal)) FROM ItemPedido ip JOIN ip.peca p JOIN ip.pedido ped WHERE ped.status = 1 AND ped.dataPedido BETWEEN :dataInicio AND :dataFim GROUP BY p.id, p.nome, p.linkFoto, p.preco, p.precoPromo ORDER BY SUM(ip.precoTotal) DESC")
    public ArrayList<ProdutoMaisPedidoDTO> findProdutosMaisPedidosPorValor(@Param("dataInicio") LocalDate var1, @Param("dataFim") LocalDate var2, Pageable var3);
}

