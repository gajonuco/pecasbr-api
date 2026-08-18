/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.controller.FinanceiroController
 *  com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO
 *  com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro
 *  com.gabriel_nunez.oficina_mecanica.service.IFluxoFinanceiroService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;
import com.gabriel_nunez.oficina_mecanica.service.IFluxoFinanceiroService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceiroController {
    @Autowired
    IFluxoFinanceiroService fluxo;

    @PostMapping(value={"/financeiro"})
    public String gerarFluxoFinanceiro(@RequestBody RegistroFinanceiroDTO registro) {
        this.fluxo.gerarFluxoFinanceiro(registro);
        return "ok";
    }

    @GetMapping(value={"/financeiro"})
    public ResponseEntity<ArrayList<ItemFinanceiroDTO>> recuperarTodos() {
        return ResponseEntity.ok(this.fluxo.recuperarRegistros());
    }

    @PutMapping(value={"/financeiro"})
    public ResponseEntity<RegistroFinanceiro> atualizarStatus(@RequestBody ItemFinanceiroDTO item) {
        try {
            RegistroFinanceiro registro = this.fluxo.alterarStatus(item);
            if (registro != null) {
                return ResponseEntity.ok(registro);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

