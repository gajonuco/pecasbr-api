package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.PecaDAO;
import com.gajonuco.pecasbr.dao.PecaVariacaoDAO;
import com.gajonuco.pecasbr.dto.PecaVariacaoDTO;
import com.gajonuco.pecasbr.dto.SalvarVariacoesDTO;
import com.gajonuco.pecasbr.model.Peca;
import com.gajonuco.pecasbr.model.PecaVariacao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PecaVariacaoService {

    private static final int MAX_CORES = 5;
    private static final int MAX_TAMANHOS = 10;

    private final PecaVariacaoDAO variacaoDAO;
    private final PecaDAO pecaDAO;

    public PecaVariacaoService(PecaVariacaoDAO variacaoDAO, PecaDAO pecaDAO) {
        this.variacaoDAO = variacaoDAO;
        this.pecaDAO = pecaDAO;
    }

    public List<PecaVariacaoDTO> listar(Integer idPeca) {
        return this.variacaoDAO.findByPecaIdOrderByCorAscTamanhoAsc(idPeca)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void salvarLote(Integer idPeca, SalvarVariacoesDTO payload) {
        Peca peca = this.pecaDAO.findById(idPeca)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada."));

        List<PecaVariacaoDTO> novas = payload.getVariacoes() != null
                ? payload.getVariacoes()
                : List.of();

        long totalCores = novas.stream().map(dto -> dto.getCor()).distinct().count();
        long totalTamanhos = novas.stream().map(dto -> dto.getTamanho()).distinct().count();

        if (totalCores > MAX_CORES)
            throw new RuntimeException("Máximo de 5 cores por peça.");
        if (totalTamanhos > MAX_TAMANHOS)
            throw new RuntimeException("Máximo de 10 tamanhos por peça.");

        this.variacaoDAO.desvinculaItensPedidoDaPeca(idPeca);
        this.variacaoDAO.flush();

        this.variacaoDAO.deleteByPecaId(idPeca);
        this.variacaoDAO.flush();

        List<PecaVariacao> entidades = novas.stream().map((PecaVariacaoDTO dto) -> {
            PecaVariacao v = new PecaVariacao();
            v.setPeca(peca);
            v.setCor(dto.getCor());
            v.setHexCode(dto.getHexCode());
            v.setTamanho(dto.getTamanho());
            v.setQuantidadeEstoque(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
            v.setSku(dto.getSku());
            return v;
        }).collect(Collectors.toList());

        this.variacaoDAO.saveAll(entidades);
        this.variacaoDAO.flush();

        int totalEstoque = this.variacaoDAO.findByPecaIdOrderByCorAscTamanhoAsc(idPeca)
                .stream()
                .mapToInt(v -> v.getQuantidadeEstoque() != null ? v.getQuantidadeEstoque() : 0)
                .sum();

        peca.setCorUnica(Boolean.TRUE.equals(payload.getCorUnica()));
        peca.setTamanhoUnico(Boolean.TRUE.equals(payload.getTamanhoUnico()));
        peca.setQuantidadeEstoque(totalEstoque);

        this.pecaDAO.save(peca);
        this.pecaDAO.flush();
    }

    @Transactional
    public PecaVariacaoDTO atualizarEstoque(Integer idVariacao, Integer novoEstoque) {
        PecaVariacao v = this.variacaoDAO.findById(idVariacao)
                .orElseThrow(() -> new RuntimeException("Variação não encontrada."));

        v.setQuantidadeEstoque(novoEstoque);
        this.variacaoDAO.save(v);
        this.variacaoDAO.flush();

        this.sincronizarEstoquePeca(v.getPeca().getId());

        return this.toDTO(v);
    }

    @Transactional
    public void remover(Integer idVariacao) {
        PecaVariacao v = this.variacaoDAO.findById(idVariacao)
                .orElseThrow(() -> new RuntimeException("Variação não encontrada."));

        Integer idPeca = v.getPeca().getId();
        this.variacaoDAO.deleteById(idVariacao);
        this.variacaoDAO.flush();

        this.sincronizarEstoquePeca(idPeca);
    }

    public PecaVariacaoDTO buscarPorCorETamanho(Integer idPeca, String cor, String tamanho) {
        return this.variacaoDAO.findByPecaIdAndCorAndTamanho(idPeca, cor, tamanho)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Variação não encontrada."));
    }

    private void sincronizarEstoquePeca(Integer idPeca) {
        List<PecaVariacao> variacoes = this.variacaoDAO.findByPecaIdOrderByCorAscTamanhoAsc(idPeca);
        int total = variacoes.stream()
                .mapToInt(v -> v.getQuantidadeEstoque() != null ? v.getQuantidadeEstoque() : 0)
                .sum();

        this.pecaDAO.findById(idPeca).ifPresent(peca -> {
            peca.setQuantidadeEstoque(total);
            this.pecaDAO.save(peca);
            this.pecaDAO.flush();
        });
    }

    private PecaVariacaoDTO toDTO(PecaVariacao v) {
        PecaVariacaoDTO dto = new PecaVariacaoDTO();
        dto.setId(v.getId());
        dto.setCor(v.getCor());
        dto.setHexCode(v.getHexCode());
        dto.setTamanho(v.getTamanho());
        dto.setQuantidadeEstoque(v.getQuantidadeEstoque());
        dto.setSku(v.getSku());
        return dto;
    }
}