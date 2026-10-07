package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.EnderecoDTO;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Endereco;

import java.util.List;

public interface IEnderecoService {
    List<Endereco> listarPorCliente(Cliente cliente);
    Endereco criar(Cliente cliente, EnderecoDTO dados);
    Endereco atualizar(Cliente cliente, int idEndereco, EnderecoDTO dados);
    void remover (Cliente cliente, int idEndereco);
    Endereco marcarComoPrincipal(Cliente cliente, int idEndereco);
    Endereco buscarPorIdDoCliente(Cliente cliente, int idEndereco);
}
