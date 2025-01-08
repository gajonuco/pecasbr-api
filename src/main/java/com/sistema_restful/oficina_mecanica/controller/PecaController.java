package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    // Cadastrar uma peça
    @PostMapping
    public ResponseEntity<Peca> salvarPeca(@RequestBody Peca peca) {
        Peca novaPeca = pecaService.salvarPeca(peca);
        return new ResponseEntity<>(novaPeca, HttpStatus.CREATED);
    }

    // Cadastrar múltiplas peças
    @PostMapping("/batch")
    public ResponseEntity<List<Peca>> salvarPecas(@RequestBody List<Peca> pecas) {
        List<Peca> pecasSalvas = pecaService.salvarPecas(pecas);
        return new ResponseEntity<>(pecasSalvas, HttpStatus.CREATED);
    }

    // Listar todas as peças
    @GetMapping
    public List<Peca> listarPecas() {
        return pecaService.listarPecas();
    }

    // Listar peças específicas por lista de IDs
    @PostMapping("/especificas")
    public ResponseEntity<List<Peca>> listarPecasEspecificas(@RequestBody List<Long> ids) {
        List<Peca> pecas = pecaService.listarPecasEspecificas(ids);
        return new ResponseEntity<>(pecas, HttpStatus.OK);
    }

    // Buscar uma peça específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPecaPorId(@PathVariable Long id) {
        return pecaService.buscarPecaPorId(id)
                .map(peca -> new ResponseEntity<>(peca, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar uma peça pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizarPeca(@PathVariable Long id, @RequestBody Peca peca) {
        return new ResponseEntity<>(pecaService.atualizarPeca(id, peca), HttpStatus.OK);
    }

    // Excluir uma peça pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        pecaService.deletarPeca(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Excluir múltiplas peças
    @DeleteMapping("/batch")
    public ResponseEntity<Void> deletarPecas(@RequestBody List<Long> ids) {
        pecaService.deletarPecas(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
