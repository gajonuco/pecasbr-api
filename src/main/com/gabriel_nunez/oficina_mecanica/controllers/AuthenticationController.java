package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.user.User;
import com.gabriel_nunez.oficina_mecanica.client.OpenCepClient;
import com.gabriel_nunez.oficina_mecanica.config.TokenService;
import com.gabriel_nunez.oficina_mecanica.dto.AuthenticationDTO;
import com.gabriel_nunez.oficina_mecanica.dto.LoginResponseDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegisterDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.UserMapper;
import com.gabriel_nunez.oficina_mecanica.repository.UserRepository;
import com.gabriel_nunez.oficina_mecanica.service.LoginService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OpenCepClient openCepClient;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data){
        return loginService.autenticarUsuario(data);
    }


    @PostMapping("/register-funcionario")
    public ResponseEntity<?> registerFuncionario(@RequestBody @Valid RegisterDTO dto) {
    if (userRepository.findByLogin(dto.login()) != null)
        return ResponseEntity.badRequest().body("Já existe um usuário com esse e-mail.");

    var endereco = openCepClient.buscar(dto.cep())
        .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o CEP fornecido."));

    String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.password());

    User user = userMapper.fromRegisterDTO(dto, endereco, senhaCriptografada);
    userRepository.save(user);

    return ResponseEntity.ok("Usuário cadastrado com sucesso!");
}

}