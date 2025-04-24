package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import com.gabriel_nunez.oficina_mecanica.dto.ClienteDTO;
import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteDTO dto, Endereco endereco) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpfCnpj(DocumentoUtils.formatarDocumento(dto.getCpfCnpj()));
        cliente.setTelefone(dto.getTelefone());
        cliente.setEndereco(endereco);
        return cliente;
    }

    public Map<String, Object> toResponse(Cliente cliente) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", cliente.getId());
        response.put("nome", cliente.getNome());
        response.put("cpf_cnpj", cliente.getCpfCnpj());
        response.put("telefone", cliente.getTelefone());
        response.put("endereco", cliente.getEndereco());

        if (cliente.getVeiculos() != null && !cliente.getVeiculos().isEmpty()) {
            var veiculos = cliente.getVeiculos().stream().map(v -> {
                Map<String, Object> veiculoMap = new LinkedHashMap<>();
                veiculoMap.put("id", v.getId());
                veiculoMap.put("placa", v.getPlaca());
                veiculoMap.put("tipo", v.getTipo());
                veiculoMap.put("marca", v.getMarca());
                veiculoMap.put("modelo", v.getModelo());
                veiculoMap.put("ano", v.getAno());
                veiculoMap.put("quilometragem", v.getQuilometragem());
                return veiculoMap;
            }).toList();
            response.put("veiculos", veiculos);
        }

        return response;
    }
}


