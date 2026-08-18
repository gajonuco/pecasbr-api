/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.controller.PedidoController
 *  com.gajonuco.pecasbr.dto.FiltroPedidoDTO
 *  com.gajonuco.pecasbr.dto.VendasPorDataDTO
 *  com.gajonuco.pecasbr.model.Cliente
 *  com.gajonuco.pecasbr.model.Pedido
 *  com.gajonuco.pecasbr.service.IClienteService
 *  com.gajonuco.pecasbr.service.IPedidoService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PatchMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.FiltroPedidoDTO;
import com.gajonuco.pecasbr.dto.VendasPorDataDTO;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Pedido;
import com.gajonuco.pecasbr.service.IClienteService;
import com.gajonuco.pecasbr.service.IPedidoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value={"*"})
@RestController
public class PedidoController {
    @Autowired
    private IPedidoService service;
    @Autowired
    private IClienteService cliService;

    @PostMapping(value={"/pedido"})
    public ResponseEntity<Pedido> inserirNovoPedido(@RequestBody Pedido novo) {
        novo.setDataPedido(LocalDate.now());
        Cliente cli = this.cliService.atualizarDados(novo.getCliente());
        novo.setCliente(cli);
        novo = this.service.inserirPedido(novo);
        if (novo != null) {
            return ResponseEntity.status((int)201).body(novo);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value={"/pedido/filtrar"})
    public ResponseEntity<ArrayList<Pedido>> buscarTodos(@RequestBody FiltroPedidoDTO parametros) {
        return ResponseEntity.ok(this.service.filtrarPorVariosCriterios(parametros));
    }

    @PatchMapping(value={"/pedido/{id}"})
    public ResponseEntity<Pedido> mudarStatus(@PathVariable(name="id") int id, @RequestParam(name="status") int status) {
        try {
            Pedido pedido = this.service.mudarStatus(id, status);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e) {
            return ResponseEntity.status((int)500).build();
        }
    }

    @GetMapping(value={"/pedido/search/{id}"})
    public ResponseEntity<Pedido> recuperarPedido(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(this.service.buscarPeloId(id));
    }

    @GetMapping(value={"/pedido/recentes"})
    public ResponseEntity<List<VendasPorDataDTO>> recuperarUltimasVendas(@RequestParam(value="inicio") String dataIni, @RequestParam(value="fim") String dataFim) {
        LocalDate inicio = LocalDate.parse(dataIni);
        LocalDate fim = LocalDate.parse(dataFim);
        return ResponseEntity.ok(this.service.recuperarTotaisUltimaSemana(inicio, fim));
    }

    @PutMapping(value={"/pedido"})
    public ResponseEntity<Pedido> atualizarPedido(@RequestBody Pedido pedido) {
        try {
            Pedido atualizado = this.service.atualizarPedido(pedido);
            if (atualizado == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(atualizado);
        }
        catch (Exception ex) {
            System.out.println("Erro ao atualizar ");
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

