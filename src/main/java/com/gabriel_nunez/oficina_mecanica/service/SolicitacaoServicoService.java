package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.SolicitacaoServicoRepository;

@Service
public class SolicitacaoServicoService {

    @Autowired
    private SolicitacaoServicoRepository repository;

    @Autowired
    private ClienteUsuarioRepository clienteUsuarioRepository;

    public SolicitacaoServico salvar(String nomeServico, ClienteUsuario clienteUsuario) {
    // Verifica se o ClienteUsuario ainda existe no banco
    ClienteUsuario existente = clienteUsuarioRepository.findById(clienteUsuario.getId())
        .orElseThrow(() -> new IllegalArgumentException("ClienteUsuario não encontrado no banco"));

    SolicitacaoServico solicitacao = new SolicitacaoServico();
    solicitacao.setNomeServico(nomeServico);
    solicitacao.setCliente(existente); // Usa a entidade gerenciada
    solicitacao.setDataHora(LocalDateTime.now());

    return repository.save(solicitacao);
}



    public List<SolicitacaoServico> listarTodas() {
        return repository.findAllByOrderByDataHoraDesc();
    }
}
