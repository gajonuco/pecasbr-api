package com.sistema_restful.oficina_mecanica.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Use AUTO para geração de UUID
    @Column(name = "role_id", columnDefinition = "UUID") // Define explicitamente como UUID
    private UUID roleId;

    @Column(nullable = false, unique = true) // Garante que os nomes sejam únicos
    private String name;

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Values {
        ADMIN(UUID.randomUUID()),
        BASIC(UUID.randomUUID());

        private final UUID roleId;

        Values(UUID roleId) {
            this.roleId = roleId;
        }

        public UUID getRoleId() {
            return roleId;
        }
    }
}


