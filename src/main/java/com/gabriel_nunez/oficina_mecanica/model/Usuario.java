package com.gabriel_nunez.oficina_mecanica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id;

    @Column(name = "username", length = 45, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 50 , nullable = false, unique = true)
    private String email;

    @Column(name = "senha", length = 100 , nullable = false)
    private String senha;

    @Column(name = "nome_usuario", length = 50, nullable = false)
    private String nome_usuario;

    @Column(name = "usuario_ativo")
    private int ativo;

    public int getId() {
        return id;
    }

   public int getAtivo() {
    return ativo;
}

public void setAtivo(int ativo) {
    this.ativo = ativo;
}

 public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    
}
