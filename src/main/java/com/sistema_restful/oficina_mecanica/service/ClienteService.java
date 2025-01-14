package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.model.Endereco;
import com.sistema_restful.oficina_mecanica.repo.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

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

    public List<Cliente> listarClientesEspecificos(List<Long> ids) {
        // Busca os clientes pelos IDs fornecidos
        List<Cliente> clientes = clienteRepository.findClientesByIds(ids);

        // Valida se todos os IDs fornecidos foram encontrados
        if (clientes.size() != ids.size()) {
            List<Long> idsEncontrados = clientes.stream()
                    .map(Cliente::getId)
                    .toList();
            List<Long> idsNaoEncontrados = ids.stream()
                    .filter(id -> !idsEncontrados.contains(id))
                    .toList();

            throw new IllegalArgumentException("IDs não encontrados: " + idsNaoEncontrados);
        }

        return clientes;
    }


    // Atualizar cliente
    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarClientePorId(id);

        // Atualiza somente os campos necessários
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());
        return clienteRepository.save(cliente);
    }

    // Excluir cliente por ID
    public void deletarCliente(Long id) {
        Cliente cliente = buscarClientePorId(id);
        clienteRepository.delete(cliente);
    }

    // Método auxiliar para buscar cliente por ID com tratamento de exceção
    private Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + id + " não encontrado."));
    }

}
