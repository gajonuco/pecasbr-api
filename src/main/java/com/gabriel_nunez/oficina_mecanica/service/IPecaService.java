/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  com.gabriel_nunez.oficina_mecanica.service.IPecaService
 *  org.springframework.data.domain.Page
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import java.util.ArrayList;
import org.springframework.data.domain.Page;

public interface IPecaService {
    public Peca inserirNovaPeca(Peca var1);

    public Peca alterarPeca(Peca var1);

    public Peca recuperarPorId(int var1);

    public ArrayList<Peca> listarTodos();

    public Page<Peca> listarDisponiveis(int var1);

    public Page<Peca> listarDestaques(int var1);

    public ArrayList<Peca> listarIndisponiveis();

    public ArrayList<Peca> listarPorCategoria(CategoriaPeca var1);

    public Page<Peca> listarPorPalavraChave(String var1, int var2);

    public ArrayList<ProdutoMaisPedidoDTO> listarProdutosMaisPedidos(FiltroRankingProdutosDTO var1);
}

