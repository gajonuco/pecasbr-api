package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.repository.PecaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    @Transactional
    public Peca salvar(Peca peca) {
        if (pecaRepository.existsBySku(peca.getSku())) {
            throw new IllegalArgumentException("Já existe uma peça cadastrada com esse SKU.");
        }

        validarEstoqueMinimo(peca.getQuantidadeEmEstoque());
        validarDataVencimento(peca.getDataVencimento());

        return pecaRepository.save(peca);
    }

    private void validarEstoqueMinimo(int quantidade) {
        if (quantidade < 5) {
            throw new IllegalArgumentException("Quantidade mínima não atingida: o estoque deve conter pelo menos 5 unidades.");
        }
    }

    private void validarDataVencimento(LocalDate vencimento) {
        if (vencimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de vencimento inválida: não é permitido cadastrar peças vencidas.");
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void atualizarPecasVencidas() {
        List<Peca> todas = pecaRepository.findAll();
        int atualizadas = 0;

        for (Peca peca : todas) {
            if (!peca.isObsoleta() && peca.getDataVencimento().isBefore(LocalDate.now())) {
                peca.setObsoleta(true);
                atualizadas++;
            }
        }

        if (atualizadas > 0) {
            pecaRepository.saveAll(todas);
        }
    }
}
