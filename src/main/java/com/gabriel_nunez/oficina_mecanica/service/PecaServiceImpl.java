package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO;
import com.gabriel_nunez.oficina_mecanica.dao.PecaDAO;
import com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

@Component
public class PecaServiceImpl implements IPecaService {

    public static final int PAGE_SIZE = 18;
    @Autowired
    private PecaDAO dao;

    @Autowired
    private CategoriaPecaDAO categoriaDAO;

    @Override
    public Peca inserirNovaPeca(Peca peca) {
        return dao.save(peca);

    }

    @Override
    public Peca alterarPeca(Peca peca) {
        // TODO Auto-generated method stub
        try {
            if (peca.getDisponivel() == 0 && peca.getQuantidadeEstoque() > 0) {
                peca.setDisponivel(1);
            }

            // Atualiza estoque
            if (peca.isEstoqueZerado()) {
                peca.setDisponivel(0);
            }
            System.out.println("estoque MINIMO   " + peca.getEstoqueMinimo());
            dao.save(peca);
            return peca;
        } catch (Exception ex) {
            System.out.println("---- PecaService.aletrarPeca()-----");
            ex.printStackTrace();
            System.out.println("-------------------------------------");
        }
        return null;
    }

    @Override
    public ArrayList<Peca> listarTodos() {
        // TODO Auto-generated method stub
        return (ArrayList<Peca>) dao.findAll();
    }

    @Override
    public Page<Peca> listarDisponiveis(int pagina) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pagina, PAGE_SIZE);

        return dao.findAllByDisponivel(1, pageable); // Considero todas as peças '1' como disponíveis

    }

    @Override
    public ArrayList<Peca> listarPorCategoria(CategoriaPeca categoriaPeca) {
        // TODO Auto-generated method stub
        return dao.findAllByCategoriaPecaAndDisponivel(categoriaPeca, 1);
    }

    @Override
    public ArrayList<Peca> listarIndisponiveis() {
        // TODO Auto-generated method stub
        return (ArrayList<Peca>) dao.findAllByDisponivel(0, null).toList();
    }

    @Override
    public Peca recuperarPorId(int idPeca) {
        // TODO Auto-generated method stub
        return dao.findById(idPeca).orElse(null);
    }

    @Override
    public Page<Peca> listarDestaques(int pagina) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pagina, PAGE_SIZE);

        return dao.findAllByDestaqueAndDisponivel(1, 1, pageable);
    }

    @Override
    public Page<Peca> listarPorPalavraChave(String palavraChave, int pagina) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pagina, PAGE_SIZE);
        return dao.findAllByDisponivelAndNomeContainingOrDisponivelAndDetalheContaining(1, palavraChave, 1,
                palavraChave, pageable);
    }

    @Override
    public ArrayList<ProdutoMaisPedidoDTO> listarProdutosMaisPedidos(FiltroRankingProdutosDTO filtro) {
        LocalDate dataInicio = filtro.getDataInicio();
        LocalDate dataFim = filtro.getDataFim();

        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Data inicial e final são obrigatórias");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
        }

        Pageable pageable = PageRequest.of(0, filtro.getLimiteProdutos());

        if ("valor".equalsIgnoreCase(filtro.getOrdenarPor())) {
            return dao.findProdutosMaisPedidosPorValor(dataInicio, dataFim, pageable);
        } else {
            return dao.findProdutosMaisPedidosPorQuantidade(dataInicio, dataFim, pageable);
        }

    }

}
