package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.request.OrcamentosServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.OrcamentosServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.OrcamentosServicoMecanicoMapper;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.model.OrcamentosServicoMecanico;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.model.TipoServicoMecanico;

import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.OrcamentosServicoMecanicoRepository;
import com.gabriel_nunez.oficina_mecanica.repository.PecaRepository;
import com.gabriel_nunez.oficina_mecanica.repository.TipoServicoMecanicoRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class OrcamentosServicoMecanicoService {

    private final OrcamentosServicoMecanicoRepository orcamentosServicoMecanicoRepository;
    private final TipoServicoMecanicoRepository tipoServicoRepository;
    private final PecaRepository pecaRepository;
    private final OrcamentosServicoMecanicoMapper orcamentosServicoMecanicoMapper;
    private final ClienteUsuarioRepository clienteUsuarioRepository;
    private final OrcamentosServicoMecanicoMapper orcamentoServicoMecanicoMapper;

    public OrcamentosServicoMecanicoService(
        OrcamentosServicoMecanicoRepository orcamentosServicoMecanicoRepository,
        TipoServicoMecanicoRepository tipoServicoRepository,
        PecaRepository pecaRepository,
        OrcamentosServicoMecanicoMapper orcamentosServicoMecanicoMapper,
        ClienteUsuarioRepository clienteUsuarioRepository,
        OrcamentosServicoMecanicoMapper orcamentoServicoMecanicoMapper
    ) {
        this.orcamentosServicoMecanicoRepository = orcamentosServicoMecanicoRepository;
        this.tipoServicoRepository = tipoServicoRepository;
        this.pecaRepository = pecaRepository;
        this.orcamentosServicoMecanicoMapper = orcamentosServicoMecanicoMapper;
        this.clienteUsuarioRepository = clienteUsuarioRepository;
        this.orcamentoServicoMecanicoMapper = orcamentoServicoMecanicoMapper;
    }

    public OrcamentosServicoMecanicoResponseDTO criarOferta(OrcamentosServicoMecanicoRequestDTO dto) {
        TipoServicoMecanico tipoServico = tipoServicoRepository.findById(dto.getTipoServicoId())
                .orElseThrow(() -> new RuntimeException("TipoServico não encontrado"));

        OrcamentosServicoMecanico orcamentosServicoMecanico = orcamentosServicoMecanicoMapper.toEntity(dto);
        orcamentosServicoMecanico.setTipoServico(tipoServico);

        // Só tenta buscar e associar cliente se o login for fornecido
        if (dto.getLoginCliente() != null && !dto.getLoginCliente().isBlank()) {
            ClienteUsuario cliente = clienteUsuarioRepository.findByLogin(dto.getLoginCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + dto.getLoginCliente()));
            orcamentosServicoMecanico.setClienteUsuario(cliente);
        }

        if (dto.getPecaId() != null) {
            Peca peca = pecaRepository.findById(dto.getPecaId())
                    .orElseThrow(() -> new IllegalArgumentException("Peça não encontrada."));
            orcamentosServicoMecanico.setPeca(peca);
        }

        

        OrcamentosServicoMecanico salvo = orcamentosServicoMecanicoRepository.save(orcamentosServicoMecanico);
       return orcamentosServicoMecanicoMapper.toDTO(salvo);

    }

    public List<OrcamentosServicoMecanicoResponseDTO> buscarPorTipoServico(Long tipoServicoId) {
    List<OrcamentosServicoMecanico> orcamentos = orcamentosServicoMecanicoRepository.findByTipoServicoIdAndAtivoTrue(tipoServicoId);
    return orcamentos.stream()
        .map(orcamentoServicoMecanicoMapper::toDTO) // conversão via mapper
        .collect(Collectors.toList());
}




}