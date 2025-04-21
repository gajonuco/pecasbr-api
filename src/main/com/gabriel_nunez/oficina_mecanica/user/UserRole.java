package com.gabriel_nunez.oficina_mecanica.user;

public enum UserRole {
    ATENDENTE("atendente"),
    MECANICA("mecanica"),
    ADMINISTRADOR("admin");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
