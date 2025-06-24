package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.OrcamentosServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.OrcamentosServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.service.OrcamentosServicoMecanicoService;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orcamentos-servico-mecanico")
public class OrcamentosServicoMecanicoController {

    private final OrcamentosServicoMecanicoService orcamentosServicoMecanicoService;
    private final SimpMessagingTemplate messagingTemplate;


    public OrcamentosServicoMecanicoController(
        OrcamentosServicoMecanicoService orcamentosServicoMecanicoService,
       SimpMessagingTemplate messagingTemplate ){
            this.orcamentosServicoMecanicoService = orcamentosServicoMecanicoService;
            this.messagingTemplate = messagingTemplate;
        }

    @PostMapping
    public OrcamentosServicoMecanicoResponseDTO criarOferta(@RequestBody OrcamentosServicoMecanicoRequestDTO dto) {
        return orcamentosServicoMecanicoService.criarOferta(dto);
    }

    @GetMapping("/tipo-servico/{id}")
    public List<OrcamentosServicoMecanicoResponseDTO> buscarPorTipoServico(@PathVariable Long id) {
        return orcamentosServicoMecanicoService.buscarPorTipoServico(id);
    }



    @MessageMapping("/selecionada")
    public void receberOfertaSelecionada(OrcamentosServicoMecanicoResponseDTO oferta) {
        messagingTemplate.convertAndSend("/topic/oferta-selecionada", oferta);
    }

}