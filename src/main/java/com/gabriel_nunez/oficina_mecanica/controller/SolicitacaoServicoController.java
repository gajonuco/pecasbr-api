/*package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.gabriel_nunez.oficina_mecanica.dto.response.SolicitacaoServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.SolicitacaoServicoMapper;
import com.gabriel_nunez.oficina_mecanica.service.SolicitacaoServicoService;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoServicoController {

    private final SolicitacaoServicoService solicitacaoServicoService;
    private final SolicitacaoServicoMapper mapper;

    public SolicitacaoServicoController(SolicitacaoServicoService solicitacaoServicoService,
                                        SolicitacaoServicoMapper mapper) {
        this.solicitacaoServicoService = solicitacaoServicoService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<SolicitacaoServicoResponseDTO> listarSolicitacoes() {
        return solicitacaoServicoService.listarTodas().stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deletarSolicitacao(@PathVariable Long id) {
        solicitacaoServicoService.deletarPorId(id);
    }

    @PostMapping("/{id}/enviar-ofertas")
    public void enviarOfertasDaSolicitacao(@PathVariable Long id) {
        solicitacaoServicoService.enviarOfertasDaSolicitacao(id);
}

}
*/