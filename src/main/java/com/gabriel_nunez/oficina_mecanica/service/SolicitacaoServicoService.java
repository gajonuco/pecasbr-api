/*package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.gabriel_nunez.oficina_mecanica.dto.response.OfertaServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.SolicitacaoServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.OfertaServicoMapper;
import com.gabriel_nunez.oficina_mecanica.mapper.SolicitacaoServicoMapper;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.model.OfertaServico;
import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;
import com.gabriel_nunez.oficina_mecanica.model.TipoServico;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.SolicitacaoServicoRepository;
import com.gabriel_nunez.oficina_mecanica.repository.TipoServicoRepository;
import com.gabriel_nunez.oficina_mecanica.repository.OfertaServicoRepository;

@Service
public class SolicitacaoServicoService {

    private final SolicitacaoServicoRepository repository;
    private final ClienteUsuarioRepository clienteUsuarioRepository;
    private final TipoServicoRepository tipoServicoRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final OfertaServicoRepository ofertaServicoRepository;
    private final OfertaServicoMapper ofertaServicoMapper;
    private final SolicitacaoServicoMapper solicitacaoServicoMapper;

    public SolicitacaoServicoService(SolicitacaoServicoRepository repository,
                                     ClienteUsuarioRepository clienteUsuarioRepository,
                                     TipoServicoRepository tipoServicoRepository,
                                     SimpMessagingTemplate messagingTemplate,
                                     OfertaServicoRepository ofertaServicoRepository,
                                     OfertaServicoMapper ofertaServicoMapper,
                                    SolicitacaoServicoMapper solicitacaoServicoMapper
                                     ) {
        this.repository = repository;
        this.clienteUsuarioRepository = clienteUsuarioRepository;
        this.tipoServicoRepository = tipoServicoRepository;
        this.messagingTemplate = messagingTemplate;
        this.ofertaServicoRepository = ofertaServicoRepository;
        this.ofertaServicoMapper = ofertaServicoMapper;
        this.solicitacaoServicoMapper = solicitacaoServicoMapper;
    }

    public SolicitacaoServico salvar(String nomeServico, ClienteUsuario clienteUsuario) {
        ClienteUsuario existente = clienteUsuarioRepository.findById(clienteUsuario.getId())
            .orElseThrow(() -> new IllegalArgumentException("ClienteUsuario não encontrado no banco"));

        TipoServico tipoServico = tipoServicoRepository.findByNome(nomeServico)
            .orElseThrow(() -> new IllegalArgumentException("TipoServico não encontrado para: " + nomeServico));

        SolicitacaoServico solicitacao = new SolicitacaoServico();
        solicitacao.setNomeServico(nomeServico);
        solicitacao.setCliente(existente);
        solicitacao.setTipoServico(tipoServico);
        solicitacao.setDataHora(LocalDateTime.now());

        

            SolicitacaoServico salvo = repository.save(solicitacao);

    // ✅ Aqui está o envio WebSocket para painel.html
    SolicitacaoServicoResponseDTO dto = solicitacaoServicoMapper.toDTO(salvo);
    messagingTemplate.convertAndSend("/topic/notifications", dto);

    return salvo;
    }

    public void enviarOfertaParaCliente(Long clienteId, OfertaServicoResponseDTO oferta) {
        messagingTemplate.convertAndSend("/topic/ofertas-cliente/" + clienteId, oferta);
    }

    public List<SolicitacaoServico> listarTodas() {
        return repository.findAllByOrderByDataHoraDesc();
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
    public void enviarOfertasDaSolicitacao(Long solicitacaoId) {
        SolicitacaoServico solicitacao = repository.findById(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        List<OfertaServico> ofertas = ofertaServicoRepository
            .findByTipoServicoIdAndAtivoTrue(solicitacao.getTipoServico().getId());

        for (OfertaServico oferta : ofertas) {
            OfertaServicoResponseDTO dto = ofertaServicoMapper.toDTO(oferta);
            enviarOfertaParaCliente(solicitacao.getCliente().getId(), dto);
        }
    }

}


*/