package com.gabriel_nunez.oficina_mecanica.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO;
import com.gabriel_nunez.oficina_mecanica.dto.FiltroPedidoDTO;
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

    @PostMapping("/pedido/filtrar")
    public ResponseEntity <ArrayList<Pedido>> buscarTodos(@RequestBody FiltroPedidoDTO parametros){
        return ResponseEntity.ok(service.filtrarPorVariosCriterios(parametros)); 
    }
    
    @PutMapping("/pedido/{id}")
    public ResponseEntity<Pedido> mudarStatus(@PathVariable(name = "id") int id, @RequestParam(name = "status") int status ){
        try {
            Pedido pedido = service.mudarStatus(id, status);
            if(pedido != null){
                return ResponseEntity.ok(pedido);
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/pedido/search/{id}")
    public ResponseEntity<Pedido> recuperarPedido(@PathVariable(name = "id") int id){
        return ResponseEntity.ok(service.buscarPeloId(id));
    }

    @GetMapping("/pedido/recentes")
    public ResponseEntity<List<VendasPorDataDTO>> recuperarUltimasVendas(@RequestParam("inicio") String dataIni, @RequestParam("fim") String dataFim ) {
        LocalDate inicio = LocalDate.parse(dataIni);
        LocalDate fim = LocalDate.parse(dataFim);
        return ResponseEntity.ok(service.recuperarTotaisUltimaSemana(inicio, fim));
    }

}
