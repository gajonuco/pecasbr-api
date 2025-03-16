package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @PostMapping
    public ResponseEntity<Object> salvarPeca(@Valid @RequestBody Peca peca, JwtAuthenticationToken token) {
        try {
            var userId = UUID.fromString(token.getName());
            Peca novaPeca = pecaService.salvarPeca(peca, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPeca);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Requisição inválida",
                    "mensagem", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao salvar a peça. Tente novamente mais tarde."
            ));
        }
    }

    @GetMapping
    public ResponseEntity<Page<Peca>> listarPecas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            JwtAuthenticationToken token) {

        var userId = UUID.fromString(token.getName());
        var isAdmin = token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        Page<Peca> pecas = isAdmin
                ? pecaService.listarTodasPecas(page, size, sortBy, direction)
                : pecaService.listarTodasPecas(userId, page, size, sortBy, direction);

        return ResponseEntity.ok(pecas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPeca(@PathVariable Long id, @RequestBody Peca pecaAtualizada, JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        var isAdmin = token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        try {
            Peca pecaAtualizadaSalva = pecaService.atualizarPeca(id, pecaAtualizada, userId, isAdmin);
            return ResponseEntity.ok(pecaAtualizadaSalva);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao atualizar a peça. Tente novamente mais tarde."
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPeca(@PathVariable Long id, JwtAuthenticationToken token) {
        var userId = UUID.fromString(token.getName());
        var isAdmin = token.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        try {
            pecaService.deletarPeca(id, userId, isAdmin);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao deletar a peça. Tente novamente mais tarde."
            ));
        }
    }
}
