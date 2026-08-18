/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.ClienteDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO
 *  com.gabriel_nunez.oficina_mecanica.model.Cliente
 *  com.gabriel_nunez.oficina_mecanica.service.ClienteServiceImpl
 *  com.gabriel_nunez.oficina_mecanica.service.IClienteService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.ClienteDAO;
import com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.service.IClienteService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteServiceImpl
implements IClienteService {
    @Autowired
    private ClienteDAO dao;

    public Cliente buscarPeloCPF(String cpf) {
        return this.dao.findByCpf(cpf);
    }

    public Cliente atualizarDados(Cliente dadosOriginais) {
        return (Cliente)this.dao.save(dadosOriginais);
    }

    public Cliente buscarPeloTefone(String telefone) {
        return this.dao.findByTelefone(telefone);
    }

    public ArrayList<Cliente> buscarPorLetra(String letra) {
        return this.dao.findByNomeStartsWith(letra);
    }

    public ArrayList<Cliente> buscarPorPalavraChave(String palavraChave) {
        return this.dao.findByNomeContaining(palavraChave);
    }

    public ArrayList<Cliente> buscarTodos() {
        return this.dao.findAllByOrderByNomeAsc();
    }

    public ArrayList<CompradorDTO> recuperarCompradores(int idPeca) {
        return this.dao.recuperarCompradores(idPeca);
    }

    public ArrayList<Cliente> buscarAniversariantes(int mes) {
        return this.dao.recuperarAniversariante(mes);
    }
}

