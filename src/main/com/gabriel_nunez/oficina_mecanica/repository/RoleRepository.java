package com.sistema_restful.oficina_mecanica.repository;

import com.sistema_restful.oficina_mecanica.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(@Param("name") String name);

}