package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.VeiculoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.VeiculoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.VeiculoMapper;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import com.gabriel_nunez.oficina_mecanica.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final VeiculoMapper veiculoMapper;

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> cadastrarVeiculo(@Valid @RequestBody VeiculoRequestDTO veiculoRequestDTO) {
        Veiculo veiculo = veiculoMapper.toEntity(veiculoRequestDTO);
        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);
        VeiculoResponseDTO respostaDTO = veiculoMapper.toResponseDTO(veiculoSalvo);

        URI location = URI.create("/veiculos/" + veiculoSalvo.getId());
        return ResponseEntity.created(location).body(respostaDTO);
    }
}
