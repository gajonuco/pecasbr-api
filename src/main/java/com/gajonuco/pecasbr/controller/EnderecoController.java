package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.EnderecoDTO;
import com.gajonuco.pecasbr.exception.EnderecoNaoEncontradoException;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Endereco;
import com.gajonuco.pecasbr.service.IClienteService;
import com.gajonuco.pecasbr.service.IEnderecoService;
import jakarta.servlet.Servlet;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnderecoController {

    private final IEnderecoService service;
    private final IClienteService clienteService;

    public EnderecoController(IEnderecoService service, IClienteService clienteService){
        this.service = service;
        this.clienteService = clienteService;
    }

    @GetMapping("/cliente/me/enderecos")
    public ResponseEntity<List<Endereco>> listar(Authentication authentication){
        return ResponseEntity.ok(service.listarPorCliente(clienteAutenticado(authentication)));
    }

    @PostMapping("/cliente/me/enderecos")
    public ResponseEntity<Endereco> criar(Authentication authentication, @RequestBody EnderecoDTO dados){
        Endereco criado = service.criar(clienteAutenticado(authentication),dados);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/cliente/me/enderecos/{id}")
    public ResponseEntity<Endereco> atualizar(Authentication authentication, @PathVariable int id, @RequestBody EnderecoDTO dados){
        try{
            return  ResponseEntity.ok(service.atualizar(clienteAutenticado(authentication),id, dados));
        } catch (EnderecoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cliente/me/enderecos/{id}")
    public ResponseEntity<Void> remover (Authentication authentication, @PathVariable int id) {
        try {
            service.remover(clienteAutenticado(authentication), id);
            return ResponseEntity.noContent().build();
        } catch (EnderecoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/cliente/me/enderecos/{id}/principal")
    public ResponseEntity<Endereco> marcarComoPrincipal(Authentication authentication, @PathVariable int id) {
        try{
            service.marcarComoPrincipal(clienteAutenticado(authentication),id);
            return ResponseEntity.ok(service.marcarComoPrincipal(clienteAutenticado(authentication), id));
        } catch (EnderecoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Cliente clienteAutenticado(Authentication authentication){
        return clienteService.buscarPeloEmail(authentication.getName());
    }

}
