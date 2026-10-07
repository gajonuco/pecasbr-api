package com.gajonuco.pecasbr.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CriarPedidoDTO(
        DadosClienteDTO cliente,
        @NotEmpty List<@Valid ItemPedidoDTO> itens,
        String observacoes,
        boolean retirar,
        Integer idEndereco,
        EnderecoDTO enderecoNovo) {
}
