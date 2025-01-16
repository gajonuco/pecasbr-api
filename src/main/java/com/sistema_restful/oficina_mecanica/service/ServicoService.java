package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.Servico;
import com.sistema_restful.oficina_mecanica.repo.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    // Salvar um serviço com validações de negócios
    public Servico salvarServico(Servico servico) {
        if (servico.getPreco() != null && servico.getPreco() < 50.0) {
            throw new IllegalArgumentException("O preço mínimo para um serviço é R$50.00.");
        }

        return servicoRepository.save(servico);
    }


    // Listar todos os serviços com paginação e ordenação
    public Page<Servico> listarServicos(Pageable pageable) {
        return servicoRepository.findAll(pageable);
    }

    // Listar serviços específicos por lista de IDs com validações
    public List<Servico> listarServicosEspecificos(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("A lista de IDs não pode ser vazia ou nula.");
        }

        List<Servico> servicos = servicoRepository.findAllById(ids);

        if (servicos.size() != ids.size()) {
            throw new IllegalArgumentException("Alguns IDs fornecidos não correspondem a serviços existentes.");
        }

        return servicos;
    }

    public Servico atualizarServico(Long id, Servico servicoAtualizado) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço com ID " + id + " não encontrado."));

        if (servicoAtualizado.getNome() != null && !servicoAtualizado.getNome().trim().isEmpty()) {
            servicoExistente.setNome(servicoAtualizado.getNome());
        }

        if (servicoAtualizado.getPreco() != null && servicoAtualizado.getPreco() > 0) {
            servicoExistente.setPreco(servicoAtualizado.getPreco());
        }

        return servicoRepository.save(servicoExistente);
    }

    public void deletarServico(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Serviço com ID " + id + " não encontrado.");
        }
        servicoRepository.deleteById(id);
    }

}
