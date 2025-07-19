package com.gabriel_nunez.oficina_mecanica.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import com.gabriel_nunez.oficina_mecanica.service.IPedidoService;

@RestController
public class PedidoController {
    
    @Autowired
    private IPedidoService service;

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> inserirNovoPedido(@RequestBody Pedido novo){
        service.inserirPedido(novo);
        if(novo != null){
            return ResponseEntity.status(201).body(novo);
        }
        return ResponseEntity.badRequest().build();
    }
    
}
