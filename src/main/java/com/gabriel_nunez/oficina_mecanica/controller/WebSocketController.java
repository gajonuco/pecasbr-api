package com.gabriel_nunez.oficina_mecanica.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

//import com.gabriel_nunez.oficina_mecanica.dto.response.SolicitacaoServicoResponseDTO;
//import com.gabriel_nunez.oficina_mecanica.mapper.SolicitacaoServicoMapper;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
//import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;
import com.gabriel_nunez.oficina_mecanica.model.OrcamentosServicoMecanico;
//import com.gabriel_nunez.oficina_mecanica.service.SolicitacaoServicoService;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /*@Autowired
    private SolicitacaoServicoService solicitacaoServicoService;

    @Autowired
    private SolicitacaoServicoMapper solicitacaoServicoMapper;*/

    // Cliente envia solicitação
    @MessageMapping("/sendMessage")
    public void receberMensagem(String nomeServico, Principal principal) {
        if (!(principal instanceof UsernamePasswordAuthenticationToken auth)) {
            throw new IllegalArgumentException("Usuário não autenticado.");
        }

        Object usuario = auth.getPrincipal();

        if (!(usuario instanceof ClienteUsuario cliente)) {
            throw new IllegalArgumentException("Apenas clientes podem enviar solicitações.");
        }

        /*SolicitacaoServico solicitacao = solicitacaoServicoService.salvar(nomeServico, cliente);
        SolicitacaoServicoResponseDTO dto = solicitacaoServicoMapper.toDTO(solicitacao);

        // Envia a resposta apenas para o cliente que solicitou, usando o login como chave
        messagingTemplate.convertAndSendToUser(
            principal.getName(), // geralmente é o login (username)
            "/queue/ofertas",
            dto
        );*/
    }

    @MessageMapping("/enviarOferta")
    public void enviarOferta(OrcamentosServicoMecanico oferta, Principal principal) {
        if (oferta == null || 
            oferta.getTipoServico() == null || 
            oferta.getTipoServico().getId() == null || 
            oferta.getClienteUsuario() == null) {
            
            throw new IllegalArgumentException("Oferta ou dados do cliente inválidos.");
        }

        messagingTemplate.convertAndSendToUser(
            oferta.getClienteUsuario().getLogin(),  // login do cliente como identificador
            "/queue/ofertas",
            oferta
        );
    }

}
