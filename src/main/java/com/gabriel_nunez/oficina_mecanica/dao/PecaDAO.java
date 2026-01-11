package com.gabriel_nunez.oficina_mecanica.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

public interface PecaDAO extends CrudRepository<Peca, Integer> {

    public Page<Peca> findAllByDisponivel(int disponivel, Pageable pageable);

    Optional<Peca> findById(int id);

    public ArrayList<Peca> findAllByCategoriaPecaAndDisponivel(CategoriaPeca categoriaPeca, int disponivel);

    public ArrayList<Peca> findAllByCategoriaPeca(CategoriaPeca categoriaPeca);

    public ArrayList<Peca> findByNomeContainingOrDetalheContaining(String keyNome, String keyDetalhe);

    public Page<Peca> findAllByDestaqueAndDisponivel(int destaque, int disponivel, Pageable pageable);

    public Page<Peca> findAllByDisponivelAndNomeContainingOrDisponivelAndDetalheContaining(int disponivel,
            String keyNome, int disponivel2, String keyDetalhe, Pageable pageable);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO( " +
            "p.id, " +
            "p.nome, " +
            "p.linkFoto, " +
            "p.preco, " +
            "p.precoPromo, " +
            "SUM(ip.qtdtItem), " +
            "SUM(ip.precoTotal)) " +
            "FROM ItemPedido ip " +
            "JOIN ip.peca p " +
            "JOIN ip.pedido ped " +
            "WHERE ped.status = 1 " +
            "AND ped.dataPedido BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY p.id, p.nome, p.linkFoto, p.preco, p.precoPromo " +
            "ORDER BY SUM(ip.qtdtItem) DESC ")
    ArrayList<ProdutoMaisPedidoDTO> findProdutosMaisPedidosPorQuantidade(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO(" +
            "p.id, " +
            "p.nome, " +
            "p.linkFoto, " +
            "p.preco, " +
            "p.precoPromo, " +
            "SUM(ip.qtdtItem), " +
            "SUM(ip.precoTotal)) " +
            "FROM ItemPedido ip " +
            "JOIN ip.peca p " +
            "JOIN ip.pedido ped " +
            "WHERE ped.status = 1 " +
            "AND ped.dataPedido BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY p.id, p.nome, p.linkFoto, p.preco, p.precoPromo " +
            "ORDER BY SUM(ip.precoTotal) DESC")
    ArrayList<ProdutoMaisPedidoDTO> findProdutosMaisPedidosPorValor(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable);

}
