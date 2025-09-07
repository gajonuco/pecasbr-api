package com.gabriel_nunez.oficina_mecanica.controller;


import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import com.gabriel_nunez.oficina_mecanica.service.IClienteService;
import com.gabriel_nunez.oficina_mecanica.service.IPedidoService;

@CrossOrigin("*")
@RestController
public class PedidoController {
    
    @Autowired
    private IPedidoService service;

    @Autowired
    private IClienteService cliService;

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> inserirNovoPedido(@RequestBody Pedido novo){

        
        novo.setDataPedido(LocalDate.now());
        Cliente cli = cliService.atualizarDados(novo.getCliente());
        novo.setCliente(cli);

        novo = service.inserirPedido(novo);
        if(novo != null){
            return ResponseEntity.status(201).body(novo);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }
    
}
