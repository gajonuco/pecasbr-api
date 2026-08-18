/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.FiltroRankingProdutosDTO
 *  com.gajonuco.pecasbr.dto.ProdutoMaisPedidoDTO
 *  com.gajonuco.pecasbr.model.CategoriaPeca
 *  com.gajonuco.pecasbr.model.Peca
 *  com.gajonuco.pecasbr.service.IPecaService
 *  org.springframework.data.domain.Page
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.FiltroRankingProdutosDTO;
import com.gajonuco.pecasbr.dto.ProdutoMaisPedidoDTO;
import com.gajonuco.pecasbr.model.CategoriaPeca;
import com.gajonuco.pecasbr.model.Peca;
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

