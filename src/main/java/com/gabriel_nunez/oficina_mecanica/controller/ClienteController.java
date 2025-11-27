package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.service.IClienteService;

@RestController
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    public IClienteService service;

    @GetMapping("/cliente/{telefone}")
    public ResponseEntity<Cliente> buscarPeloTelefone(@PathVariable String telefone) {
        Cliente resultado = service.buscarPeloTefone(telefone);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    // @GetMapping("/cliente/{cpf}")
    // public ResponseEntity<Cliente> buscarPeloCPF (@PathVariable String cpf){
    // Cliente resultado = service.buscarPeloCPF(cpf);
    // if(resultado != null){
    // return ResponseEntity.ok(resultado);
    // }
    // return ResponseEntity.notFound().build();
    // }

    @GetMapping("/cliente/nome/{letra}")
    public ResponseEntity<ArrayList<Cliente>> buscarPorLetra(@PathVariable String letra) {
        return ResponseEntity.ok(service.buscarPorLetra(letra));
    }

    @PostMapping("/cliente")
    public ResponseEntity<Cliente> adicionarNovoCliente(@RequestBody Cliente novo) {
        try {
            Cliente cli = service.atualizarDados(novo);
            if (cli != null) {
                return ResponseEntity.status(201).body(cli);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao incluir Novo Cliente");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/cliente")
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente cli = service.atualizarDados(cliente);
            return ResponseEntity.ok(cli);
        } catch (Exception ex) {
            System.out.println("Erro ao Atualizar cliente existente");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/cliente")
    public ResponseEntity<ArrayList<Cliente>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/cliente/compras/{id}")
    public ResponseEntity<ArrayList<CompradorDTO>> recuperarCompradores(@PathVariable("id") int idPeca) {
        return ResponseEntity.ok(service.recuperarCompradores(idPeca));
    }

    @GetMapping("/cliente/busca/{keyword}")
    public ResponseEntity<ArrayList<Cliente>> buscarPorPalavraChave(@PathVariable String keyword) {
        return ResponseEntity.ok(service.buscarPorPalavraChave(keyword));
    }

    @GetMapping("/cliente/aniversario/{mes}")
    public ResponseEntity<ArrayList<Cliente>> recuperarAniversariante(@PathVariable int mes) {
        return ResponseEntity.ok(service.buscarAniversariantes(mes));
    }
}
