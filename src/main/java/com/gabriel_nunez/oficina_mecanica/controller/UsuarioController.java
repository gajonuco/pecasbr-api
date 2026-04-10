/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.controller.UsuarioController
 *  com.gabriel_nunez.oficina_mecanica.model.Usuario
 *  com.gabriel_nunez.oficina_mecanica.security.JWTToken
 *  com.gabriel_nunez.oficina_mecanica.security.JWTTokenUtil
 *  com.gabriel_nunez.oficina_mecanica.service.IUsuarioService
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

import com.gabriel_nunez.oficina_mecanica.model.Usuario;
import com.gabriel_nunez.oficina_mecanica.security.JWTToken;
import com.gabriel_nunez.oficina_mecanica.security.JWTTokenUtil;
import com.gabriel_nunez.oficina_mecanica.service.IUsuarioService;
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

@CrossOrigin(value={"*"})
@RestController
public class UsuarioController {
    @Autowired
    private IUsuarioService service;

    @PostMapping(value={"/login"})
    public ResponseEntity<JWTToken> fazerLogin(@RequestBody Usuario dadosLogin) {
        System.out.println("dados login: " + String.valueOf(dadosLogin));
        Usuario user = this.service.recuperarUsuario(dadosLogin);
        if (user != null) {
            JWTToken jwtToken = new JWTToken();
            jwtToken.setToken(JWTTokenUtil.generateToken((Usuario)user));
            return ResponseEntity.ok(jwtToken);
        }
        Usuario userInativo = this.service.buscarUsuarioPorCredenciais(dadosLogin);
        if (userInativo != null && userInativo.getAtivo() == 0) {
            return ResponseEntity.status((int)403).build();
        }
        return ResponseEntity.status((int)401).build();
    }

    @GetMapping(value={"/usuario"})
    public ResponseEntity<ArrayList<Usuario>> recuperarTodos() {
        return ResponseEntity.ok(this.service.recuperarTodos());
    }

    @PostMapping(value={"/usuario"})
    public ResponseEntity<Usuario> adicionarNovo(@RequestBody Usuario novo) {
        Usuario res = this.service.adicionarNovo(novo);
        if (res != null) {
            return ResponseEntity.status((int)201).body(res);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value={"/usuario/{id}"})
    public ResponseEntity<Usuario> alterarDados(@RequestBody Usuario usuario, @PathVariable int id) {
        usuario.setId(id);
        Usuario res = this.service.atualizarUsuario(usuario);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value={"/usuario/{id}"})
    public ResponseEntity<Usuario> recuperarPeloId(@PathVariable int id) {
        Usuario res = this.service.recuerarPeloId(id);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
}

