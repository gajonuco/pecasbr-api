package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.PecaRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.PecaResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.PecaMapper;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.service.PecaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
public class PecaController {

    private final PecaService pecaService;
    private final PecaMapper pecaMapper;

    @PostMapping
    public ResponseEntity<PecaResponseDTO> cadastrarPeca(@Valid @RequestBody PecaRequestDTO dto) {
        Peca peca = pecaMapper.toEntity(dto);
        Peca salva = pecaService.salvar(peca);

        URI location = URI.create("/pecas/" + salva.getId());
        return ResponseEntity.created(location).body(pecaMapper.toResponse(salva));
    }
}
