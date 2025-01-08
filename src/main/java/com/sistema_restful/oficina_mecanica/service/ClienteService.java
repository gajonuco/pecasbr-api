package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.repo.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Salvar um cliente
    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Salvar múltiplos clientes
    public List<Cliente> salvarClientes(List<Cliente> clientes) {
        return clienteRepository.saveAll(clientes);
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Listar clientes específicos por lista de IDs
    public List<Cliente> listarClientesEspecificos(List<Long> ids) {
        return clienteRepository.findAllById(ids);
    }

    // Buscar cliente por ID
    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Atualizar cliente
    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setTelefone(clienteAtualizado.getTelefone());
            cliente.setEndereco(clienteAtualizado.getEndereco());
            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    // Excluir cliente por ID
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    // Excluir múltiplos clientes por lista de IDs
    public void deletarClientes(List<Long> ids) {
        clienteRepository.deleteAllById(ids);
    }
}
