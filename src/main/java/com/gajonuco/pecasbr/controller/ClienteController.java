/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.controller.ClienteController
 *  com.gajonuco.pecasbr.dto.CompradorDTO
 *  com.gajonuco.pecasbr.model.Cliente
 *  com.gajonuco.pecasbr.service.IClienteService
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
package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.CadastroClienteDTO;
import com.gajonuco.pecasbr.dto.CompradorDTO;
import com.gajonuco.pecasbr.dto.LoginClienteDTO;
import com.gajonuco.pecasbr.exception.ClienteJaCadastradoException;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.security.JWTToken;
import com.gajonuco.pecasbr.security.JWTTokenUtil;
import com.gajonuco.pecasbr.service.IClienteService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    private final IClienteService service;
    private final JWTTokenUtil jwtTokenUtil;

    public ClienteController(IClienteService service, JWTTokenUtil jwtTokenUtil) {
        this.service = service;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/cliente/cadastro")
    public ResponseEntity<JWTToken> cadastrar(@RequestBody CadastroClienteDTO dados){
        try {
            Cliente cliente = service.cadastrar(dados);
            JWTToken token = new JWTToken();
            token.setToken(jwtTokenUtil.generateToken(cliente));
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (ClienteJaCadastradoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/cliente/login")
    public ResponseEntity<JWTToken> login(@RequestBody LoginClienteDTO dados){
        Cliente cliente = service.autenticar(dados);
        if (cliente == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        JWTToken token = new JWTToken();
        token.setToken(jwtTokenUtil.generateToken(cliente));
        return  ResponseEntity.ok(token);
    }

    @GetMapping("/cliente/me")
    public ResponseEntity<Cliente> meusDados(Authentication authentication){
        Cliente cliente = service.buscarPeloEmail(authentication.getName());
        if(cliente == null) {
            return ResponseEntity.notFound().build();
        }
        cliente.setSenha(null); //nunca devolve o hash
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/cliente/me")
    public ResponseEntity<Cliente> atualizarMeusDados(Authentication authentication,@RequestBody Cliente dados) {
        Cliente cliente = service.buscarPeloEmail(authentication.getName());
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        // escopo deliberadamente limitado: e-mail, senha e endereços não mudam por aqui
        // (login tem endpoint próprio; endereços são a sub-issue 4)
        cliente.setNome(dados.getNome());
        cliente.setTelefone(dados.getTelefone());
        cliente.setDataNasc(dados.getDataNasc());

        Cliente atualizado = service.atualizarDados(cliente);
        atualizado.setSenha(null);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping(value={"/cliente/nome/{letra}"})
    public ResponseEntity<ArrayList<Cliente>> buscarPorLetra(@PathVariable String letra) {
        return ResponseEntity.ok(this.service.buscarPorLetra(letra));
    }

    @PostMapping(value={"/cliente"})
    public ResponseEntity<Cliente> adicionarNovoCliente(@RequestBody Cliente novo) {
        try {
            Cliente cli = this.service.atualizarDados(novo);
            if (cli != null) {
                return ResponseEntity.status((int)201).body(cli);
            }
        }
        catch (Exception ex) {
            System.out.println("Erro ao incluir Novo Cliente");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value={"/cliente"})
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente cli = this.service.atualizarDados(cliente);
            return ResponseEntity.ok(cli);
        }
        catch (Exception ex) {
            System.out.println("Erro ao Atualizar cliente existente");
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value={"/cliente"})
    public ResponseEntity<ArrayList<Cliente>> buscarTodos() {
        return ResponseEntity.ok(this.service.buscarTodos());
    }

    @GetMapping(value={"/cliente/compras/{id}"})
    public ResponseEntity<ArrayList<CompradorDTO>> recuperarCompradores(@PathVariable(value="id") int idPeca) {
        return ResponseEntity.ok(this.service.recuperarCompradores(idPeca));
    }

    @GetMapping(value={"/cliente/busca/{keyword}"})
    public ResponseEntity<ArrayList<Cliente>> buscarPorPalavraChave(@PathVariable String keyword) {
        return ResponseEntity.ok(this.service.buscarPorPalavraChave(keyword));
    }

    @GetMapping(value={"/cliente/aniversario/{mes}"})
    public ResponseEntity<ArrayList<Cliente>> recuperarAniversariante(@PathVariable int mes) {
        return ResponseEntity.ok(this.service.buscarAniversariantes(mes));
    }
}

