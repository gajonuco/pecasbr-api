package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public interface IClienteService {
    
    public Cliente buscarPeloTefone(String telefone);
    public Cliente atualizarDados(Cliente dadosOriginais);
    public Cliente buscarPeloCPF(String cpf);
    public ArrayList<Cliente> buscarPorLetra(String letra);
    public ArrayList<Cliente> buscarPorPalavraChave(String palavraChave);
    public ArrayList<Cliente> buscarTodos();
    public ArrayList<Cliente> buscarAniversariantes(int mes);

    public ArrayList<CompradorDTO> recuperarCompradores(int idPeca);
}
