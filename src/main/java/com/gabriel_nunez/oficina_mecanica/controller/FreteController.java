/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.controller.FreteController
 *  com.gabriel_nunez.oficina_mecanica.model.Frete
 *  com.gabriel_nunez.oficina_mecanica.service.IFreteService
 *  io.swagger.v3.oas.annotations.Operation
 *  io.swagger.v3.oas.annotations.security.SecurityRequirement
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.model.Frete;
import com.gabriel_nunez.oficina_mecanica.service.IFreteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@CrossOrigin(value={"*"})
public class FreteController {
    @Autowired
    private IFreteService service;

    @GetMapping(value={"/fretesdisponiveis"})
    public ResponseEntity<ArrayList<Frete>> buscarDisponiveis() {
        return ResponseEntity.ok(this.service.recuperarDisponiveis());
    }

    @GetMapping(value={"/fretes/{id}"})
    public ResponseEntity<Frete> recuperarPeloId(@PathVariable int id) {
        Frete res = this.service.recuperarPeloId(id);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value={"/fretes/prefixo/{prefixo}"})
    public ResponseEntity<Frete> recuperarPeloPrefixo(@PathVariable String prefixo) {
        Frete res = this.service.recuperarPeloPrefixo(prefixo);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value={"/fretes"})
    @Operation(summary="fretes", security={@SecurityRequirement(name="bearerAuth")})
    public ResponseEntity<ArrayList<Frete>> buscarTodos() {
        return ResponseEntity.ok(this.service.recuperarTodos());
    }

    @PostMapping(value={"/fretes"})
    public ResponseEntity<Frete> adicionarNovo(@RequestBody Frete novo) {
        try {
            Frete adicionado = this.service.inserirFrete(novo);
            if (adicionado != null) {
                return ResponseEntity.status((int)201).body(adicionado);
            }
        }
        catch (Exception ex) {
            System.out.println("DEBUG - Erro ao gravar FRETE " + ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value={"/fretes"})
    public ResponseEntity<Frete> atualizarFrete(@RequestBody Frete frete) {
        try {
            Frete atualizado = this.service.atualizarFrete(frete);
            if (atualizado != null) {
                return ResponseEntity.ok(atualizado);
            }
        }
        catch (Exception ex) {
            System.out.println("DEBUG - Erro ao atualizar FRETE " + ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }
}

