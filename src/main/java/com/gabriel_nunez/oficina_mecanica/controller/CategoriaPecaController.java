/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.controller.CategoriaPecaController
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.service.ICategoriaPecaService
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

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.service.ICategoriaPecaService;
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

@CrossOrigin(value={"*"})
@RestController
public class CategoriaPecaController {
    @Autowired
    ICategoriaPecaService service;

    @GetMapping(value={"/categoria_peca"})
    public ResponseEntity<ArrayList<CategoriaPeca>> listarTodas() {
        return ResponseEntity.ok().body(this.service.recuperarTodasCategoriasPecas());
    }

    @GetMapping(value={"/categoria_by_id"})
    public ResponseEntity<ArrayList<CategoriaPeca>> recuperarTodasOrdenadasPeloId() {
        return ResponseEntity.ok(this.service.recuperarTodasPeloId());
    }

    @GetMapping(value={"categoria_peca/search"})
    public ResponseEntity<ArrayList<CategoriaPeca>> recuperarPorPalavraChave(@RequestParam(name="key") String palavraChave) {
        if (palavraChave != null) {
            return ResponseEntity.ok().body(this.service.recuperarPorPalavraChave(palavraChave));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value={"categoria_peca/{id}"})
    public ResponseEntity<CategoriaPeca> recuperarDetalhes(@PathVariable(name="id") int id) {
        CategoriaPeca resultado = this.service.recuperaPorID(id);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value={"/categoria_peca"})
    public ResponseEntity<CategoriaPeca> adicionarNovaCategoriaPeca(@RequestBody CategoriaPeca categoriaPeca) {
        CategoriaPeca resultado;
        if (categoriaPeca.getId() != null) {
            categoriaPeca.setId(null);
        }
        if ((resultado = this.service.adicionarNovaCategoriaPeca(categoriaPeca)) != null) {
            return ResponseEntity.status((int)201).body(resultado);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value={"/categoria_peca"})
    public ResponseEntity<CategoriaPeca> alterarCategoriaPeca(@RequestBody CategoriaPeca categoriaPeca) {
        CategoriaPeca resultado = this.service.alterarCategoriaPeca(categoriaPeca);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.badRequest().build();
    }
}

