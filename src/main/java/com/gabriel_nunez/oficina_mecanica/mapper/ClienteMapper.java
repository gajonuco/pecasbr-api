package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.request.ClienteRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.ClienteResponseDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.VeiculoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO dto, Endereco endereco) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpfCnpj(dto.getCpfCnpj());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEndereco(endereco);
        return cliente;
    }

    public ClienteResponseDTO toResponse(Cliente cliente) {
        return ClienteResponseDTO.builder()
            .id(cliente.getId())
            .nome(cliente.getNome())
            .cpfCnpj(cliente.getCpfCnpj())
            .telefone(cliente.getTelefone())
            .endereco(cliente.getEndereco())
            .veiculos(cliente.getVeiculos() != null ? mapVeiculos(cliente.getVeiculos()) : null)
            .build();
    }

    private List<VeiculoResponseDTO> mapVeiculos(List<Veiculo> veiculos) {
        return veiculos.stream()
                .map(v -> VeiculoResponseDTO.builder()
                        .id(v.getId())
                        .tipo(v.getTipo())
                        .placa(v.getPlaca())
                        .marca(v.getMarca())
                        .modelo(v.getModelo())
                        .ano(v.getAno())
                        .quilometragem(v.getQuilometragem())
                        .build())
                .collect(Collectors.toList());
    }
}
