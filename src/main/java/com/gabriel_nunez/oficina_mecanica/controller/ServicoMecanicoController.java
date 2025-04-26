package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.ServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.service.ServicoMecanicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/servicos")
public class ServicoMecanicoController {

    private final ServicoMecanicoService servicoMecanicoService;

    public ServicoMecanicoController(ServicoMecanicoService servicoMecanicoService) {
        this.servicoMecanicoService = servicoMecanicoService;
    }

    @PostMapping
    public ResponseEntity<ServicoMecanicoResponseDTO> criarServico(@RequestBody @Valid ServicoMecanicoRequestDTO dto) {
        ServicoMecanicoResponseDTO response = servicoMecanicoService.criarServico(dto);
        URI location = URI.create("/servicos/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }
}
