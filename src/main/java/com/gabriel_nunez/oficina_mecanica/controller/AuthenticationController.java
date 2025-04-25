package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.user.User;
import com.gabriel_nunez.oficina_mecanica.client.OpenCepClient;
import com.gabriel_nunez.oficina_mecanica.dto.AuthenticationDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegisterDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.UserMapper;
import com.gabriel_nunez.oficina_mecanica.repository.UserRepository;
import com.gabriel_nunez.oficina_mecanica.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final OpenCepClient openCepClient;
    private final UserMapper userMapper;
    private final LoginService loginService;

    public AuthenticationController(UserRepository userRepository,
                                    OpenCepClient openCepClient,
                                    UserMapper userMapper,
                                    LoginService loginService) {
        this.userRepository = userRepository;
        this.openCepClient = openCepClient;
        this.userMapper = userMapper;
        this.loginService = loginService;
    }

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
