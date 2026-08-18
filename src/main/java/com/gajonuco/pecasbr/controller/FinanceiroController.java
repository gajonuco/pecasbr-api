/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.controller.FinanceiroController
 *  com.gajonuco.pecasbr.dto.ItemFinanceiroDTO
 *  com.gajonuco.pecasbr.dto.RegistroFinanceiroDTO
 *  com.gajonuco.pecasbr.model.RegistroFinanceiro
 *  com.gajonuco.pecasbr.service.IFluxoFinanceiroService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.ItemFinanceiroDTO;
import com.gajonuco.pecasbr.dto.RegistroFinanceiroDTO;
import com.gajonuco.pecasbr.model.RegistroFinanceiro;
import com.gajonuco.pecasbr.service.IFluxoFinanceiroService;
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

