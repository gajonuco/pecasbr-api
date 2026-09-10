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
import com.gajonuco.pecasbr.dto.CadastroClienteDTO;
import com.gajonuco.pecasbr.dto.CompradorDTO;
import com.gajonuco.pecasbr.dto.LoginClienteDTO;
import com.gajonuco.pecasbr.exception.ClienteJaCadastradoException;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.service.IClienteService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ClienteServiceImpl implements IClienteService {

    private final ClienteDAO dao;
    private final PasswordEncoder passwordEncoder;

    public ClienteServiceImpl(ClienteDAO dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente buscarPeloCPF(String cpf) {
        return this.dao.findByCpf(cpf);
    }

    public Cliente atualizarDados(Cliente dadosOriginais) {
        return (Cliente) this.dao.save(dadosOriginais);
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


    public Cliente cadastrar(CadastroClienteDTO dadosCadastro) {
        Cliente existente = dao.findByEmail(dadosCadastro.email());

        if (existente == null && dadosCadastro.cpf() != null) {
            existente = dao.findByCpf(dadosCadastro.cpf());
        }

        if (existente != null) {
            if (existente.getSenha() != null) {
                throw new ClienteJaCadastradoException();
            }
            existente.setSenha(passwordEncoder.encode(dadosCadastro.senha()));
            existente.setEmail(dadosCadastro.email());
            return dao.save(existente);
        }


        Cliente novo = new Cliente();
        novo.setNome(dadosCadastro.nome());
        novo.setEmail(dadosCadastro.email());
        novo.setSenha(passwordEncoder.encode(dadosCadastro.senha()));
        novo.setCpf(dadosCadastro.cpf());
        novo.setTelefone(dadosCadastro.telefone());
        novo.setDataNasc(dadosCadastro.dataNasc());
        return dao.save(novo);
    }

    public Cliente autenticar(LoginClienteDTO dadosLogin) {
        Cliente cliente = dao.findByEmail(dadosLogin.email());
        if(cliente == null || cliente.getSenha() == null){
            return null;
        }
        if(!passwordEncoder.matches(dadosLogin.senha(), cliente.getSenha())) {
            return null;
        }
        return cliente;
    }

    public ArrayList<Cliente> buscarAniversariantes(int mes) {
        return this.dao.recuperarAniversariante(mes);
    }
}

