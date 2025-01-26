package com.sistema_restful.oficina_mecanica.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.sistema_restful.oficina_mecanica.model.Role;
import com.sistema_restful.oficina_mecanica.model.User;
import com.sistema_restful.oficina_mecanica.repository.RoleRepository;
import com.sistema_restful.oficina_mecanica.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Inicializando configuração de usuário admin...");

        // Buscar ou criar a Role ADMIN
        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName(Role.Values.ADMIN.name());
            roleAdmin = roleRepository.save(roleAdmin);
            System.out.println("Role 'ADMIN' criada no banco de dados.");
        } else {
            System.out.println("Role 'ADMIN' já existe.");
        }

        // Verificar se o usuário admin já existe
        Optional<User> existingAdminUser = userRepository.findByUsername("admin");
        if (existingAdminUser.isPresent()) {
            System.out.println("Usuário admin já existe no banco de dados.");
            return;
        }

        // Criar o usuário admin
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("123")); // Senha codificada com BCrypt
        adminUser.setRoles(Set.of(roleAdmin)); // Relacionar a Role "ADMIN"

        userRepository.save(adminUser);
        System.out.println("Usuário admin adicionado diretamente no banco de dados.");
    }


}