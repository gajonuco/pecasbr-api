/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO
 *  com.gabriel_nunez.oficina_mecanica.dao.PecaDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  com.gabriel_nunez.oficina_mecanica.service.IPecaService
 *  com.gabriel_nunez.oficina_mecanica.service.PecaServiceImpl
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.domain.Page
 *  org.springframework.data.domain.PageRequest
 *  org.springframework.data.domain.Pageable
 *  org.springframework.stereotype.Component
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO;
import com.gabriel_nunez.oficina_mecanica.dao.PecaDAO;
import com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.service.IPecaService;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PecaServiceImpl
implements IPecaService {
    public static final int PAGE_SIZE = 18;
    @Autowired
    private PecaDAO dao;
    @Autowired
    private CategoriaPecaDAO categoriaDAO;

    public Peca inserirNovaPeca(Peca peca) {
        return (Peca)this.dao.save(peca);
    }

    public Peca alterarPeca(Peca peca) {
        boolean temVariacoes;
        Peca pecaExistente = (Peca)this.dao.findById(peca.getId()).orElseThrow(() -> new RuntimeException("Pe\u00e7a n\u00e3o encontrada: id=" + peca.getId()));
        peca.setVariacoes(pecaExistente.getVariacoes());
        peca.setImagens(pecaExistente.getImagens());
        boolean bl = temVariacoes = pecaExistente.getVariacoes() != null && !pecaExistente.getVariacoes().isEmpty();
        if (temVariacoes) {
            int total = pecaExistente.getVariacoes().stream().mapToInt(v -> v.getQuantidadeEstoque() != null ? v.getQuantidadeEstoque() : 0).sum();
            peca.setQuantidadeEstoque(Integer.valueOf(total));
        } else {
            peca.setQuantidadeEstoque(Integer.valueOf(peca.getQuantidadeEstoque() != null ? peca.getQuantidadeEstoque() : 0));
        }
        int estoqueEfetivo = peca.getQuantidadeEstoque();
        if (estoqueEfetivo <= 0) {
            peca.setDisponivel(0);
        } else {
            peca.setDisponivel(1);
        }
        return (Peca)this.dao.save(peca);
    }

    public ArrayList<Peca> listarTodos() {
        return (ArrayList)this.dao.findAll();
    }

    public Page<Peca> listarDisponiveis(int pagina) {
        PageRequest pageable = PageRequest.of((int)pagina, (int)18);
        return this.dao.findAllByDisponivel(1, (Pageable)pageable);
    }

    public ArrayList<Peca> listarPorCategoria(CategoriaPeca categoriaPeca) {
        return this.dao.findAllByCategoriaPecaAndDisponivel(categoriaPeca, 1);
    }

    public ArrayList<Peca> listarIndisponiveis() {
        return (ArrayList)this.dao.findAllByDisponivel(0, null).toList();
    }

    public Peca recuperarPorId(int idPeca) {
        return this.dao.findById(idPeca).orElse(null);
    }

    public Page<Peca> listarDestaques(int pagina) {
        PageRequest pageable = PageRequest.of((int)pagina, (int)18);
        return this.dao.findAllByDestaqueAndDisponivel(1, 1, (Pageable)pageable);
    }

    public Page<Peca> listarPorPalavraChave(String palavraChave, int pagina) {
        PageRequest pageable = PageRequest.of((int)pagina, (int)18);
        return this.dao.findAllByDisponivelAndNomeContainingOrDisponivelAndDetalheContaining(1, palavraChave, 1, palavraChave, (Pageable)pageable);
    }

    public ArrayList<ProdutoMaisPedidoDTO> listarProdutosMaisPedidos(FiltroRankingProdutosDTO filtro) {
        LocalDate dataInicio = filtro.getDataInicio();
        LocalDate dataFim = filtro.getDataFim();
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Data inicial e final s\u00e3o obrigat\u00f3rias.");
        }
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial n\u00e3o pode ser posterior \u00e0 data final.");
        }
        PageRequest pageable = PageRequest.of((int)0, (int)filtro.getLimiteProdutos());
        if ("valor".equalsIgnoreCase(filtro.getOrdenarPor())) {
            return this.dao.findProdutosMaisPedidosPorValor(dataInicio, dataFim, (Pageable)pageable);
        }
        return this.dao.findProdutosMaisPedidosPorQuantidade(dataInicio, dataFim, (Pageable)pageable);
    }
}

