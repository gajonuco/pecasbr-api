/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.ClienteDAO
 *  com.gajonuco.pecasbr.dto.CompradorDTO
 *  com.gajonuco.pecasbr.model.Cliente
 *  com.gajonuco.pecasbr.service.ClienteServiceImpl
 *  com.gajonuco.pecasbr.service.IClienteService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.ClienteDAO;
import com.gajonuco.pecasbr.dto.CompradorDTO;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.service.IClienteService;
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

