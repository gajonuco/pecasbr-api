/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO
 *  com.gabriel_nunez.oficina_mecanica.model.Cliente
 *  com.gabriel_nunez.oficina_mecanica.service.IClienteService
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
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

