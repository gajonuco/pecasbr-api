package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.ClienteDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import com.gabriel_nunez.oficina_mecanica.service.ClienteService;
import com.gabriel_nunez.oficina_mecanica.service.EnderecoService;
import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;
import java.util.Map;
import java.util.LinkedHashMap;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ClienteService clienteService;

@PostMapping
public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {

    // Validação do CPF/CNPJ usando DocumentoUtils
    if (!DocumentoUtils.validarDocumento(clienteDTO.getCpfCnpj())) {
        return ResponseEntity.badRequest().body("CPF/CNPJ inválido!");
    }

    // Formatando o CPF/CNPJ antes de salvar
    String cpfCnpjFormatado = DocumentoUtils.formatarDocumento(clienteDTO.getCpfCnpj());

    // Validação do CEP antes da requisição externa
    if (clienteDTO.getCep() != null && !clienteDTO.getCep().matches("\\d{8}")) {
        return ResponseEntity.badRequest().body("CEP inválido. Deve conter 8 dígitos numéricos.");
    }

    // Busca o endereço pelo CEP apenas se o CEP for enviado
    Endereco endereco = null;
    if (clienteDTO.getCep() != null) {
        endereco = enderecoService.buscarEnderecoPorCep(clienteDTO.getCep());
        if (endereco == null) {
            return ResponseEntity.badRequest().body("Endereço não encontrado para o CEP fornecido.");
        }
    }


    // Mapeando DTO para a entidade Cliente
    Cliente cliente = new Cliente();
    cliente.setNome(clienteDTO.getNome());
    cliente.setCpfCnpj(cpfCnpjFormatado); // Salvando CPF/CNPJ formatado
    cliente.setEmail(clienteDTO.getEmail());
    cliente.setTelefone(clienteDTO.getTelefone());
    cliente.setEndereco(endereco);

    // Salvar Cliente e Retornar Resposta
    try {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        
        // Construindo a resposta conforme esperado
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", clienteSalvo.getId());
        response.put("nome", clienteSalvo.getNome());
        response.put("cpf_cnpj", clienteSalvo.getCpfCnpj());
        response.put("email", clienteSalvo.getEmail());
        response.put("telefone", clienteSalvo.getTelefone());
        response.put("endereco", clienteSalvo.getEndereco());

        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
}

}