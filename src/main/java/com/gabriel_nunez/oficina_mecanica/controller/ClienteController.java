package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.service.IClienteService;

@RestController
@CrossOrigin("*")
public class ClienteController {
    
    @Autowired
    public IClienteService service;

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<Cliente> buscarPeloCPF (@PathVariable String cpf){
       Cliente resultado = service.buscarPeloCPF(cpf);
        if(resultado != null){
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/nome/{letra}")
    public ResponseEntity <ArrayList<Cliente>> buscarPorLetra (@PathVariable String letra){
        return ResponseEntity.ok(service.buscarPorLetra(letra));

    }

    @GetMapping("/cliente")
    public ResponseEntity <ArrayList<Cliente>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/cliente/{keyword}")
    public ResponseEntity <ArrayList<Cliente>> buscarPorPalavraChave (@PathVariable String keyword) {
        return ResponseEntity.ok(service.buscarPorPalavraChave(keyword));
    }
}
