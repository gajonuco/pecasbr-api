package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.VeiculoDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.VeiculoMapper;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import com.gabriel_nunez.oficina_mecanica.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoDTO> cadastrarVeiculo(@Valid @RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = VeiculoMapper.toEntity(veiculoDTO);
        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);
        VeiculoDTO respostaDTO = VeiculoMapper.toDTO(veiculoSalvo);

        URI location = URI.create("/veiculos/" + veiculoSalvo.getId());
        return ResponseEntity.created(location).body(respostaDTO);
    }
}
