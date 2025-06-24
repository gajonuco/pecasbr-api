package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.request.OrcamentosServicoMecanicoRequestDTO;

import com.gabriel_nunez.oficina_mecanica.dto.response.OrcamentosServicoMecanicoResponseDTO;

import com.gabriel_nunez.oficina_mecanica.model.OrcamentosServicoMecanico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrcamentosServicoMecanicoMapper {

    @Mapping(source = "tipoServico.nome", target = "nomeTipoServico")
    @Mapping(source = "peca.nome", target = "nomePeca")
    @Mapping(source = "peca.marca", target = "marcaPeca")
    @Mapping(source = "peca.dataVencimento", target = "dataVencimentoPeca")
    @Mapping(source = "clienteUsuario.cliente.nome", target = "nomeCliente")

   OrcamentosServicoMecanicoResponseDTO toDTO(OrcamentosServicoMecanico orcamentosServicoMecanico);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoServico", ignore = true)
    @Mapping(target = "peca", ignore = true)
    @Mapping(target = "clienteUsuario", ignore = true)
    OrcamentosServicoMecanico toEntity(OrcamentosServicoMecanicoRequestDTO dto);
}
