package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);

}