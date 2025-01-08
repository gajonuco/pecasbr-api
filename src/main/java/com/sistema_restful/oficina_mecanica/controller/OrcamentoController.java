package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.dto.OrcamentoDTO;
import com.sistema_restful.oficina_mecanica.model.Orcamento;
import com.sistema_restful.oficina_mecanica.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    // Cadastrar um orçamento
    @PostMapping
    public ResponseEntity<Orcamento> salvarOrcamento(@RequestBody OrcamentoDTO orcamentoDTO) {
        Orcamento novoOrcamento = orcamentoService.salvarOrcamento(orcamentoDTO);
        return new ResponseEntity<>(novoOrcamento, HttpStatus.CREATED);
    }

    // Cadastrar múltiplos orçamentos
    @PostMapping("/batch")
    public ResponseEntity<List<Orcamento>> salvarOrcamentos(@RequestBody List<OrcamentoDTO> orcamentosDTO) {
        List<Orcamento> orcamentosSalvos = orcamentoService.salvarOrcamentos(orcamentosDTO);
        return new ResponseEntity<>(orcamentosSalvos, HttpStatus.CREATED);
    }

    // Listar todos os orçamentos
    @GetMapping
    public List<Orcamento> listarOrcamentos() {
        return orcamentoService.listarOrcamentos();
    }

    // Listar orçamentos específicos por uma lista de IDs
    @PostMapping("/especificos")
    public ResponseEntity<List<Orcamento>> listarOrcamentosEspecificos(@RequestBody List<Long> ids) {
        List<Orcamento> orcamentos = orcamentoService.listarOrcamentosEspecificos(ids);
        return new ResponseEntity<>(orcamentos, HttpStatus.OK);
    }

    // Buscar um orçamento específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarOrcamentoPorId(@PathVariable Long id) {
        return orcamentoService.buscarOrcamentoPorId(id)
                .map(orcamento -> new ResponseEntity<>(orcamento, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar um orçamento por ID
    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizarOrcamento(@PathVariable Long id, @RequestBody OrcamentoDTO orcamentoDTO) {
        Orcamento orcamentoAtualizado = orcamentoService.atualizarOrcamento(id, orcamentoDTO);
        return new ResponseEntity<>(orcamentoAtualizado, HttpStatus.OK);
    }

    // Excluir um orçamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrcamento(@PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Excluir múltiplos orçamentos
    @DeleteMapping("/batch")
    public ResponseEntity<Void> deletarOrcamentos(@RequestBody List<Long> ids) {
        orcamentoService.deletarOrcamentos(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
