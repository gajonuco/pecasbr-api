package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.TipoServicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.TipoServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.service.TipoServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tipos-servicos")
@RequiredArgsConstructor
public class TipoServicoController {

    private final TipoServicoService tipoServicoService;

    @PostMapping
    public ResponseEntity<TipoServicoResponseDTO> criar(@Valid @RequestBody TipoServicoRequestDTO dto) {
        TipoServicoResponseDTO response = tipoServicoService.criarTipoServico(dto);
        URI location = URI.create("/tipos-servicos/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoServicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tipoServicoService.listarTodos());
    }
    

}
