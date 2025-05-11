package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;
import com.gabriel_nunez.oficina_mecanica.service.SolicitacaoServicoService;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoServicoController {

    @Autowired
    private SolicitacaoServicoService solicitacaoServicoService;

    @GetMapping
    public List<SolicitacaoServico> listarSolicitacoes() {
        return solicitacaoServicoService.listarTodas();
    }
}
