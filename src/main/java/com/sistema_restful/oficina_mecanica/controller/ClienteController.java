package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = clienteService.salvarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (IllegalArgumentException ex) {
            // Retorna uma mensagem detalhada para erros de validação ou lógica de negócio
            return ResponseEntity.badRequest().body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "erro", "Requisição inválida",
                    "mensagem", ex.getMessage(),
                    "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception ex) {
            // Retorna uma mensagem detalhada para erros inesperados
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
            @RequestParam(defaultValue = "nome") String sortBy
    ) {
        Page<Cliente> clientes = clienteService.listarClientes(page, size, sortBy);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/especificos")
    public ResponseEntity<Object> listarClientesEspecificos(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "A lista de IDs não pode ser nula ou vazia."
            ));
        }

        List<Cliente> clientes = clienteService.listarClientesEspecificos(ids);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "erro", "Nenhum cliente foi encontrado com os IDs fornecidos."
            ));
        }

        return ResponseEntity.ok(clientes);
    }



    // Atualizar um cliente pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteService.atualizarCliente(id, cliente), HttpStatus.OK);
    }

    // Excluir um cliente pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
