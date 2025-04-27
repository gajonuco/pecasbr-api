package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.request.ClienteRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.ClienteResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.ClienteMapper;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.service.ClienteService;
import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
        if (!DocumentoUtils.validarDocumento(clienteDTO.getCpfCnpj())) {
            return ResponseEntity.badRequest().build();
        }

        Cliente cliente = clienteService.salvar(clienteDTO);
        URI location = URI.create("/clientes/" + cliente.getId());
        ClienteResponseDTO response = clienteMapper.toResponse(cliente);
        return ResponseEntity.created(location).body(response);
    }
}
