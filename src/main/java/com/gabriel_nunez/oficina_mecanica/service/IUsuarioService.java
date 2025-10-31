package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.Usuario;

public interface IUsuarioService {
    
    public Usuario recuperarUsuario(Usuario original);
    public ArrayList<Usuario> recuperarTodos();
    public Usuario adicionarNovo(Usuario novo);
    public Usuario atualizarUsuario(Usuario user);
    public Usuario recuerarPeloId(int id);
}
