/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.controller.PecaVariacaoController
 *  com.gajonuco.pecasbr.dto.PecaVariacaoDTO
 *  com.gajonuco.pecasbr.dto.SalvarVariacoesDTO
 *  com.gajonuco.pecasbr.service.PecaVariacaoService
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PatchMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.PecaVariacaoDTO;
import com.gajonuco.pecasbr.dto.SalvarVariacoesDTO;
import com.gajonuco.pecasbr.service.PecaVariacaoService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/pecas/{idPeca}/variacoes"})
public class PecaVariacaoController {
    private final PecaVariacaoService service;

    public PecaVariacaoController(PecaVariacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PecaVariacaoDTO>> listar(@PathVariable Integer idPeca) {
        return ResponseEntity.ok(this.service.listar(idPeca));
    }

    @PutMapping
    public ResponseEntity<Void> salvarLote(@PathVariable Integer idPeca, @RequestBody SalvarVariacoesDTO payload) {
        this.service.salvarLote(idPeca, payload);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value={"/{idVariacao}/estoque"})
    public ResponseEntity<PecaVariacaoDTO> atualizarEstoque(@PathVariable Integer idPeca, @PathVariable Integer idVariacao, @RequestBody Map<String, Integer> body) {
        Integer novoEstoque = body.get("quantidadeEstoque");
        return ResponseEntity.ok(this.service.atualizarEstoque(idVariacao, novoEstoque));
    }

    @DeleteMapping(value={"/{idVariacao}"})
    public ResponseEntity<Void> remover(@PathVariable Integer idPeca, @PathVariable Integer idVariacao) {
        this.service.remover(idVariacao);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value={"/disponibilidade"})
    public ResponseEntity<PecaVariacaoDTO> disponibilidade(@PathVariable Integer idPeca, @RequestParam String cor, @RequestParam String tamanho) {
        return ResponseEntity.ok(this.service.buscarPorCorETamanho(idPeca, cor, tamanho));
    }
}

