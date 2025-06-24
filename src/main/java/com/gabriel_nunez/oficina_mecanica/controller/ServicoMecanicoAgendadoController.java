package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.ServicoMecanicoAgendadoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.ServicoMecanicoAgendadoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.service.ServicoMecanicoAgendadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/servicos")
public class ServicoMecanicoAgendadoController {

    private final ServicoMecanicoAgendadoService servicoMecanicoAgendadoService;

    public ServicoMecanicoAgendadoController(ServicoMecanicoAgendadoService servicoMecanicoAgendadoService) {
        this.servicoMecanicoAgendadoService = servicoMecanicoAgendadoService;
    }

   /*@PostMapping
    public ResponseEntity<ServicoMecanicoAgendadoResponseDTO> criarServico(@RequestBody @Valid ServicoMecanicoAgendadoRequestDTO dto) {
        ServicoMecanicoAgendadoResponseDTO response = servicoMecanicoAgendadoService.criarServico(dto);
        URI location = URI.create("/servicos/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }*/

    @PostMapping
    public ResponseEntity<?> agendarServico(@RequestBody ServicoMecanicoAgendadoRequestDTO dto) {
        servicoMecanicoAgendadoService.criarServico(dto);
        return ResponseEntity.ok("Agendamento realizado com sucesso!");
    }

}
