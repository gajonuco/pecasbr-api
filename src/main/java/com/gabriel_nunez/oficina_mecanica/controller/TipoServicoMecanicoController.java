package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.TipoServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.TipoServicoMecanicoResponseDTO;

import com.gabriel_nunez.oficina_mecanica.service.TipoServicoMecanicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tipos-servicos")
@RequiredArgsConstructor
public class TipoServicoMecanicoController {

    private final TipoServicoMecanicoService tipoServicoMecanicoService;

    @PostMapping
    public ResponseEntity<TipoServicoMecanicoResponseDTO> criar(@Valid @RequestBody TipoServicoMecanicoRequestDTO dto) {
        TipoServicoMecanicoResponseDTO response = tipoServicoMecanicoService.criarTipoServico(dto);
        URI location = URI.create("/tipos-servicos/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoServicoMecanicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tipoServicoMecanicoService.listarTodos());
    }
    

}
