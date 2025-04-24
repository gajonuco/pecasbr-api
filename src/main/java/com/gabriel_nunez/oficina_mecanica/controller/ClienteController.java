package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.ClienteDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.ClienteMapper;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.service.ClienteService;
import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        if (!DocumentoUtils.validarDocumento(clienteDTO.getCpfCnpj())) {
            return ResponseEntity.badRequest().body("CPF/CNPJ inválido!");
        }

        try {
            Cliente salvo = clienteService.salvar(clienteDTO);
            return ResponseEntity.ok(clienteMapper.toResponse(salvo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
