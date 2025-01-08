package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.repo.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    // Salvar uma peça
    public Peca salvarPeca(Peca peca) {
        return pecaRepository.save(peca);
    }

    // Salvar múltiplas peças
    public List<Peca> salvarPecas(List<Peca> pecas) {
        return pecaRepository.saveAll(pecas);
    }

    // Listar todas as peças
    public List<Peca> listarPecas() {
        return pecaRepository.findAll();
    }

    // Listar peças específicas por lista de IDs
    public List<Peca> listarPecasEspecificas(List<Long> ids) {
        return pecaRepository.findAllById(ids);
    }

    // Buscar peça por ID
    public Optional<Peca> buscarPecaPorId(Long id) {
        return pecaRepository.findById(id);
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

    // Excluir múltiplas peças por lista de IDs
    public void deletarPecas(List<Long> ids) {
        pecaRepository.deleteAllById(ids);
    }
}
