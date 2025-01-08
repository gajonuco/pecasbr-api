package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Cadastrar um cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.salvarCliente(cliente);
        return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
    }

    // Cadastrar múltiplos clientes
    @PostMapping("/batch")
    public ResponseEntity<List<Cliente>> cadastrarClientes(@RequestBody List<Cliente> clientes) {
        List<Cliente> clientesSalvos = clienteService.salvarClientes(clientes);
        return new ResponseEntity<>(clientesSalvos, HttpStatus.CREATED);
    }

    // Listar todos os clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    // Listar clientes específicos por IDs
    @PostMapping("/especificos")
    public ResponseEntity<List<Cliente>> listarClientesEspecificos(@RequestBody List<Long> ids) {
        List<Cliente> clientes = clienteService.listarClientesEspecificos(ids);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Buscar um cliente específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id)
                .map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    // Excluir múltiplos clientes
    @DeleteMapping("/batch")
    public ResponseEntity<Void> deletarClientes(@RequestBody List<Long> ids) {
        clienteService.deletarClientes(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
