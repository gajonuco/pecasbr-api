package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.repo.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    public Peca salvarPeca(Peca peca) {
        if (peca.getNome() == null || peca.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da peça é obrigatório.");
        }
        if (peca.getPreco() == null || peca.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço da peça deve ser maior que zero.");
        }
        return pecaRepository.save(peca);
    }


    // Listar todas as peças com paginação e ordenação
    public Page<Peca> listarPecas(Pageable pageable) {
        return pecaRepository.findAll(pageable);
    }

    // Listar peças específicas por lista de IDs
    public List<Peca> listarPecasEspecificas(List<Long> ids) {
        List<Peca> pecas = pecaRepository.findAllById(ids);

        // Valida se todos os IDs foram encontrados
        if (pecas.size() != ids.size()) {
            List<Long> idsEncontrados = pecas.stream()
                    .map(Peca::getId)
                    .toList();
            List<Long> idsNaoEncontrados = ids.stream()
                    .filter(id -> !idsEncontrados.contains(id))
                    .toList();
            throw new IllegalArgumentException("IDs não encontrados: " + idsNaoEncontrados);
        }

        return pecas;
    }
    // Atualizar uma peça pelo ID
    public Peca atualizarPeca(Long id, Peca pecaAtualizada) {
        return pecaRepository.findById(id).map(peca -> {
            peca.setNome(pecaAtualizada.getNome());
            peca.setPreco(pecaAtualizada.getPreco());
            return pecaRepository.save(peca);
        }).orElseThrow(() -> new RuntimeException("Peça não encontrada"));
    }

    // Excluir uma peça pelo ID
    public void deletarPeca(Long id) {
        pecaRepository.deleteById(id);
    }

}
