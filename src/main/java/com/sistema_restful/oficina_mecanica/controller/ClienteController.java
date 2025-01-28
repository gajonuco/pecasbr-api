package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> cadastrarCliente(@Valid @RequestBody Cliente cliente, JwtAuthenticationToken token) {
        try {
            // Obtém o ID do usuário logado a partir do token
            var userId = UUID.fromString(token.getName());

            // Salva o cliente com o autor vinculado
            Cliente clienteSalvo = clienteService.salvarClienteComAutor(cliente, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "erro", "Requisição inválida",
                    "mensagem", ex.getMessage(),
                    "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao processar sua requisição. Tente novamente mais tarde.",
                    "timestamp", System.currentTimeMillis()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<Page<Cliente>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            JwtAuthenticationToken token
    ) {
        var userId = UUID.fromString(token.getName());

        var isAdmin = token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        Page<Cliente> clientes;
        if (isAdmin) {
            // Admin pode listar todos os clientes
            clientes = clienteService.listarTodosClientes(page, size, sortBy);
        } else {
            // Usuário comum lista apenas os clientes que ele cadastrou
            clientes = clienteService.listarClientesPorAutor(userId, page, size, sortBy);
        }

        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody Cliente clienteAtualizado,
            JwtAuthenticationToken token
    ) {
        var userId = UUID.fromString(token.getName());

        var isAdmin = token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        try {
            Cliente clienteAtualizadoSalvo = clienteService.atualizarCliente(id, clienteAtualizado, userId, isAdmin);
            return ResponseEntity.ok(clienteAtualizadoSalvo);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatus()).body(Map.of(
                    "erro", ex.getReason()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao processar sua requisição. Tente novamente mais tarde.",
                    "timestamp", System.currentTimeMillis()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable Long id, JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());

        var isAdmin = token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        try {
            clienteService.deletarCliente(id, userId, isAdmin);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatus()).body(Map.of(
                    "erro", ex.getReason()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao processar sua requisição. Tente novamente mais tarde.",
                    "timestamp", System.currentTimeMillis()
            ));
        }
    }
}
