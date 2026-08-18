/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.CompradorDTO
 *  com.gajonuco.pecasbr.model.Cliente
 *  com.gajonuco.pecasbr.service.IClienteService
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.CompradorDTO;
import com.gajonuco.pecasbr.model.Cliente;
import java.util.ArrayList;

public interface IClienteService {
    public Cliente buscarPeloTefone(String var1);

    public Cliente atualizarDados(Cliente var1);

    public Cliente buscarPeloCPF(String var1);

    public ArrayList<Cliente> buscarPorLetra(String var1);

    public ArrayList<Cliente> buscarPorPalavraChave(String var1);

    public ArrayList<Cliente> buscarTodos();

    public ArrayList<Cliente> buscarAniversariantes(int var1);

    public ArrayList<CompradorDTO> recuperarCompradores(int var1);
}

