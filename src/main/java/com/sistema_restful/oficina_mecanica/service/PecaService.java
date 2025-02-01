package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.repository.PecaRepository;
import com.sistema_restful.oficina_mecanica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    @Autowired
    private UserRepository userRepository;

    public Peca salvarPeca(Peca peca, UUID userId) {

        if (peca.getNome() == null || peca.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da peça é obrigatório.");
        }
        if (peca.getPreco() == null || peca.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço da peça deve ser maior que zero.");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        peca.setUser(user); // Define o usuário logado como autor da peça
        return pecaRepository.save(peca);
    }

    public Page<Peca> listarTodasPecas(Pageable pageable, UUID userId, boolean isAdmin) {
        return isAdmin
                ? pecaRepository.findAll(pageable) // ADMIN vê todas as peças
                : pecaRepository.findByUserId(userId, pageable); // Usuário comum vê só as próprias peças
    }

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

    public Peca atualizarPeca(Long id, Peca pecaAtualizada, UUID userId, boolean isAdmin) {
        Peca pecaExistente = pecaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Peça com ID " + id + " não encontrada."));

        if (!isAdmin && !pecaExistente.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar esta peça.");
        }

        if (pecaAtualizada.getNome() != null && !pecaAtualizada.getNome().trim().isEmpty()) {
            pecaExistente.setNome(pecaAtualizada.getNome());
        }

        if (pecaAtualizada.getPreco() != null && pecaAtualizada.getPreco() > 0) {
            pecaExistente.setPreco(pecaAtualizada.getPreco());
        }

        return pecaRepository.save(pecaExistente);
    }

    public void deletarPeca(Long id, UUID userId, boolean isAdmin) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Peça com ID " + id + " não encontrada."));

        if (!isAdmin && !peca.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir esta peça.");
        }

        pecaRepository.deleteById(id);
    }
}
