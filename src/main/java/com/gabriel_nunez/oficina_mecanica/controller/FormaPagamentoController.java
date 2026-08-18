/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.controller.FormaPagamentoController
 *  com.gabriel_nunez.oficina_mecanica.model.FormaPagamento
 *  com.gabriel_nunez.oficina_mecanica.service.IFormaPgtoService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import com.gabriel_nunez.oficina_mecanica.service.IFormaPgtoService;
import java.util.ArrayList;
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

@RestController
@CrossOrigin(value={"*"})
public class FormaPagamentoController {
    @Autowired
    private IFormaPgtoService service;

    @GetMapping(value={"/formaspagamento"})
    public ResponseEntity<ArrayList<FormaPagamento>> recuperarTodas(@RequestParam(name="visivel") String visivel) {
        if (visivel.equals("1")) {
            return ResponseEntity.ok(this.service.buscarVisiveis());
        }
        return ResponseEntity.ok(this.service.buscarTodas());
    }

    @GetMapping(value={"/formaspagamento/{id}"})
    public ResponseEntity<FormaPagamento> recuperarPeloId(@PathVariable int id) {
        FormaPagamento forma = this.service.buscarPeloId(id);
        if (forma != null) {
            return ResponseEntity.ok(forma);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value={"/formaspagamento"})
    public ResponseEntity<FormaPagamento> inserirNovo(@RequestBody FormaPagamento novo) {
        try {
            if (this.service.atualizar(novo) != null) {
                return ResponseEntity.ok(novo);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value={"/formaspagamento"})
    public ResponseEntity<FormaPagamento> atualizar(@RequestBody FormaPagamento novo) {
        try {
            if (this.service.atualizar(novo) != null) {
                return ResponseEntity.ok(novo);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

