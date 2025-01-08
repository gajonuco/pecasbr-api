package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.dto.OrcamentoDTO;
import com.sistema_restful.oficina_mecanica.model.*;
import com.sistema_restful.oficina_mecanica.repo.ClienteRepository;
import com.sistema_restful.oficina_mecanica.repo.OrcamentoRepository;
import com.sistema_restful.oficina_mecanica.repo.PecaRepository;
import com.sistema_restful.oficina_mecanica.repo.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PecaRepository pecaRepository;

    public Orcamento salvarOrcamento(OrcamentoDTO orcamentoDTO) {
        Orcamento orcamento = new Orcamento();
        return processarOrcamento(orcamentoDTO, orcamento);
    }

    public List<Orcamento> salvarOrcamentos(List<OrcamentoDTO> orcamentosDTO) {
        return orcamentosDTO.stream()
                .map(this::salvarOrcamento)
                .collect(Collectors.toList());
    }

    public List<Orcamento> listarOrcamentos() {
        return orcamentoRepository.findAll();
    }

    public List<Orcamento> listarOrcamentosEspecificos(List<Long> ids) {
        return orcamentoRepository.findAllById(ids);
    }

    public Optional<Orcamento> buscarOrcamentoPorId(Long id) {
        return orcamentoRepository.findById(id);
    }

    public Orcamento atualizarOrcamento(Long id, OrcamentoDTO orcamentoDTO) {
        return orcamentoRepository.findById(id).map(orcamento -> processarOrcamento(orcamentoDTO, orcamento))
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

    public void deletarOrcamento(Long id) {
        orcamentoRepository.deleteById(id);
    }

    public void deletarOrcamentos(List<Long> ids) {
        orcamentoRepository.deleteAllById(ids);
    }

    private Orcamento processarOrcamento(OrcamentoDTO orcamentoDTO, Orcamento orcamento) {
        // Obter o cliente pelo ID
        Cliente cliente = clienteRepository.findById(orcamentoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        orcamento.setCliente(cliente);
        orcamento.setDataCriacao(LocalDate.now());

        // Processar e associar serviços com quantidades ao orçamento
        List<OrcamentoServico> orcamentoServicos = orcamentoDTO.getServicos().stream()
                .map(dto -> {
                    Servico servico = servicoRepository.findById(dto.getServicoId())
                            .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                    OrcamentoServico orcamentoServico = new OrcamentoServico();
                    orcamentoServico.setServico(servico);
                    orcamentoServico.setQuantidade(dto.getQuantidade());
                    orcamentoServico.setOrcamento(orcamento);
                    return orcamentoServico;
                })
                .collect(Collectors.toList());
        orcamento.setOrcamentoServicos(orcamentoServicos);

        // Processar e associar peças com quantidades ao orçamento
        List<OrcamentoPeca> orcamentoPecas = orcamentoDTO.getPecas().stream()
                .map(dto -> {
                    Peca peca = pecaRepository.findById(dto.getPecaId())
                            .orElseThrow(() -> new RuntimeException("Peça não encontrada"));
                    OrcamentoPeca orcamentoPeca = new OrcamentoPeca();
                    orcamentoPeca.setPeca(peca);
                    orcamentoPeca.setQuantidade(dto.getQuantidade());
                    orcamentoPeca.setOrcamento(orcamento);
                    return orcamentoPeca;
                })
                .collect(Collectors.toList());
        orcamento.setOrcamentoPecas(orcamentoPecas);

        // Calcular valor total e desconto
        orcamento.calcularValorTotal();
        return orcamentoRepository.save(orcamento);
    }
}
