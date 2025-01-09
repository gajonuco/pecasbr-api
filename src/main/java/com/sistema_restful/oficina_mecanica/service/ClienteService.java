package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.model.Endereco;
import com.sistema_restful.oficina_mecanica.repo.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }

        // Validações básicas
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do cliente é obrigatório.");
        }

        if (!cliente.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("O e-mail informado é inválido.");
        }

        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este e-mail.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do cliente é obrigatório.");
        }

        if (!cliente.getTelefone().matches("\\d{10,11}")) {
            throw new IllegalArgumentException("O telefone deve conter 10 ou 11 dígitos.");
        }

        // Validações de endereço
        Endereco endereco = cliente.getEndereco();
        if (endereco != null) {
            if (endereco.getRua() == null || endereco.getRua().trim().isEmpty()) {
                throw new IllegalArgumentException("A rua do endereço é obrigatória.");
            }
            if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
                throw new IllegalArgumentException("A cidade do endereço é obrigatória.");
            }
            if (endereco.getCep() != null && !endereco.getCep().matches("\\d{5}-\\d{3}")) {
                throw new IllegalArgumentException("O CEP informado é inválido.");
            }
        }

        // Salvando o cliente
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Erro ao salvar cliente: dados inválidos ou violação de integridade.", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado ao salvar o cliente.", ex);
        }
    }



    public Page<Cliente> listarClientes(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return clienteRepository.findAll(pageable);
    }

    // Listar clientes específicos por lista de IDs
    public List<Cliente> listarClientesEspecificos(List<Long> ids) {
        return clienteRepository.findAllById(ids);
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
