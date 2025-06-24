/*package com.gabriel_nunez.oficina_mecanica.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.gabriel_nunez.oficina_mecanica.dto.response.OfertaSelecionadaResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.model.OfertaSelecionada;
import com.gabriel_nunez.oficina_mecanica.model.OfertaServico;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.OfertaSelecionadaRepository;
import com.gabriel_nunez.oficina_mecanica.repository.OfertaServicoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OfertaSelecionadaService {

    private final OfertaSelecionadaRepository repository;
    private final OfertaServicoRepository ofertaServicoRepository;
    private final ClienteUsuarioRepository clienteUsuarioRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public OfertaSelecionadaService(
        OfertaSelecionadaRepository repository,
        OfertaServicoRepository ofertaServicoRepository,
        ClienteUsuarioRepository clienteUsuarioRepository,
        SimpMessagingTemplate messagingTemplate
    ) {
        this.repository = repository;
        this.ofertaServicoRepository = ofertaServicoRepository;
        this.clienteUsuarioRepository = clienteUsuarioRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public OfertaSelecionada salvar(OfertaSelecionada entidade) {
        OfertaSelecionada salvo = repository.save(entidade);

    OfertaSelecionadaResponseDTO dto = new OfertaSelecionadaResponseDTO(
        salvo.getId(),
        salvo.getClienteUsuario().getCliente().getNome(),
        salvo.getOfertaOriginal().getTipoServico().getNome(),
        salvo.getDescricao(),
        salvo.getPreco(),
        salvo.getDataHoraSelecionada()
    );


        messagingTemplate.convertAndSend("/topic/notifications", dto);
        return salvo;
    }

    public List<OfertaSelecionada> listarPorCliente(Long clienteId) {
        return repository.findByClienteUsuarioId(clienteId);
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Oferta selecionada não encontrada");
        }
        repository.deleteById(id);
    }

}
*/