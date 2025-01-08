package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Servico;
import com.sistema_restful.oficina_mecanica.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    // Adicionar um serviço
    @PostMapping
    public ResponseEntity<Servico> salvarServico(@RequestBody Servico servico) {
        Servico novoServico = servicoService.salvarServico(servico);
        return new ResponseEntity<>(novoServico, HttpStatus.CREATED);
    }

    // Adicionar múltiplos serviços
    @PostMapping("/batch")
    public ResponseEntity<List<Servico>> salvarServicos(@RequestBody List<Servico> servicos) {
        List<Servico> servicosSalvos = servicoService.salvarServicos(servicos);
        return new ResponseEntity<>(servicosSalvos, HttpStatus.CREATED);
    }

    // Listar todos os serviços
    @GetMapping
    public List<Servico> listarServicos() {
        return servicoService.listarServicos();
    }

    // Listar serviços específicos por uma lista de IDs
    @PostMapping("/especificos")
    public ResponseEntity<List<Servico>> listarServicosEspecificos(@RequestBody List<Long> ids) {
        List<Servico> servicos = servicoService.listarServicosEspecificos(ids);
        return new ResponseEntity<>(servicos, HttpStatus.OK);
    }

    // Buscar um serviço específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarServicoPorId(@PathVariable Long id) {
        return servicoService.buscarServicoPorId(id)
                .map(servico -> new ResponseEntity<>(servico, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar um serviço por ID
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        return new ResponseEntity<>(servicoService.atualizarServico(id, servico), HttpStatus.OK);
    }

    // Excluir um serviço por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletarServico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Excluir múltiplos serviços
    @DeleteMapping("/batch")
    public ResponseEntity<Void> deletarServicos(@RequestBody List<Long> ids) {
        servicoService.deletarServicos(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
