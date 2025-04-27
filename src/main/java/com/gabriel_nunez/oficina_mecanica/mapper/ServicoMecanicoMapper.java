package com.gabriel_nunez.oficina_mecanica.mapper;


import com.gabriel_nunez.oficina_mecanica.dto.response.ServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.ServicoMecanico;
import org.springframework.stereotype.Component;

@Component
public class ServicoMecanicoMapper {

    public ServicoMecanicoResponseDTO toDTO(ServicoMecanico servico) {
        ServicoMecanicoResponseDTO dto = new ServicoMecanicoResponseDTO();
        dto.setId(servico.getId());
        dto.setStatus(servico.getStatus());
        dto.setValorTotal(servico.getValorTotal());
        dto.setDataInicio(servico.getDataInicio());
        dto.setDataConclusao(servico.getDataConclusao());
        dto.setClienteId(servico.getCliente().getId());
        dto.setVeiculoId(servico.getVeiculo().getId());
        dto.setPecasUtilizadasIds(servico.getPecasUtilizadas().stream().map(p -> p.getId()).toList());
        dto.setMecanicosResponsaveisIds(servico.getMecanicosResponsaveis().stream().map(m -> m.getId()).toList());
        return dto;
    }
}
