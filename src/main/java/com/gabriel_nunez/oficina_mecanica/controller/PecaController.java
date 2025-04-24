package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.PecaDTO;
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

    @PostMapping
    public ResponseEntity<PecaDTO> cadastrarPeca(@Valid @RequestBody PecaDTO pecaDTO) {
        Peca peca = PecaMapper.toEntity(pecaDTO);
        Peca salva = pecaService.salvar(peca);
        URI location = URI.create("/pecas/" + salva.getId());
        return ResponseEntity.created(location).body(PecaMapper.toDTO(salva));
    }
}
