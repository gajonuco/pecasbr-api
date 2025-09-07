package com.gabriel_nunez.oficina_mecanica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.Usuario;
import com.gabriel_nunez.oficina_mecanica.security.JWTTokenUtil;
import com.gabriel_nunez.oficina_mecanica.security.JWTToken;
import com.gabriel_nunez.oficina_mecanica.service.IUsuarioService;

@CrossOrigin("*")
@RestController
public class UsuarioController {
    
    @Autowired
    private IUsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<JWTToken> fazerLogin(@RequestBody Usuario dadosLogin){
        Usuario user = service.recuperarUsuario(dadosLogin);
        if (user != null){

                JWTToken jwtToken = new JWTToken();

                jwtToken.setToken(JWTTokenUtil.generateToken(user));

                return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(403).build();
    }
}
