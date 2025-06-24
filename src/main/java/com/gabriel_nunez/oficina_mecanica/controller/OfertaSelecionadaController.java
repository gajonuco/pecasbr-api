/*package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.dto.request.OfertaSelecionadaRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.OfertaSelecionadaResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.OfertaSelecionadaMapper;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.model.OfertaSelecionada;
import com.gabriel_nunez.oficina_mecanica.model.OfertaServico;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.OfertaServicoRepository;
import com.gabriel_nunez.oficina_mecanica.service.OfertaSelecionadaService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ofertas-selecionadas")
@RequiredArgsConstructor
public class OfertaSelecionadaController {

    private final OfertaSelecionadaService service;
    private final OfertaSelecionadaMapper mapper;

    private final OfertaServicoRepository ofertaServicoRepo;
    private final ClienteUsuarioRepository clienteRepo;

    @PostMapping
    public ResponseEntity<OfertaSelecionadaResponseDTO> salvar(@RequestBody OfertaSelecionadaRequestDTO dto) {
        OfertaServico oferta = ofertaServicoRepo.findById(dto.getOfertaOriginalId())
            .orElseThrow(() -> new EntityNotFoundException("Oferta de serviço não encontrada"));

        ClienteUsuario cliente = clienteRepo.findById(dto.getClienteUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        OfertaSelecionada entidade = mapper.toEntity(dto, oferta, cliente);
        OfertaSelecionada salvo = service.salvar(entidade);

        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<OfertaSelecionadaResponseDTO>> listarPorCliente(@PathVariable Long id) {
        List<OfertaSelecionada> lista = service.listarPorCliente(id);
        return ResponseEntity.ok(lista.stream()
                .map(mapper::toDTO)
                .toList());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id); // lógica no service que faz a deleção
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
*/