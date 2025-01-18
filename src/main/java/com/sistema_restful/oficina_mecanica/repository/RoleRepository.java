package com.sistema_restful.oficina_mecanica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema_restful.oficina_mecanica.model.Role;
import com.sistema_restful.oficina_mecanica.model.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}