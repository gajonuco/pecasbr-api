package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.model.Servico;
import com.sistema_restful.oficina_mecanica.repo.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    // Salvar um serviço
    public Servico salvarServico(Servico servico) {
        return servicoRepository.save(servico);
    }

    // Salvar múltiplos serviços
    public List<Servico> salvarServicos(List<Servico> servicos) {
        return servicoRepository.saveAll(servicos);
    }

    // Listar todos os serviços
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    // Listar serviços específicos por uma lista de IDs
    public List<Servico> listarServicosEspecificos(List<Long> ids) {
        return servicoRepository.findAllById(ids);
    }

    // Buscar serviço por ID
    public Optional<Servico> buscarServicoPorId(Long id) {
        return servicoRepository.findById(id);
    }

    // Atualizar um serviço por ID
    public Servico atualizarServico(Long id, Servico servicoAtualizado) {
        return servicoRepository.findById(id).map(servico -> {
            servico.setNome(servicoAtualizado.getNome());
            servico.setPreco(servicoAtualizado.getPreco());
            return servicoRepository.save(servico);
        }).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    // Excluir um serviço por ID
    public void deletarServico(Long id) {
        servicoRepository.deleteById(id);
    }

    // Excluir múltiplos serviços por lista de IDs
    public void deletarServicos(List<Long> ids) {
        servicoRepository.deleteAllById(ids);
    }
}
