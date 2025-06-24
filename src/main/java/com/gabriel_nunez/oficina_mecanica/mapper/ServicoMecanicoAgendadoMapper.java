package com.gabriel_nunez.oficina_mecanica.mapper;


import com.gabriel_nunez.oficina_mecanica.dto.response.ServicoMecanicoAgendadoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.ServicoMecanicoAgendado;
import org.springframework.stereotype.Component;

@Component
public class ServicoMecanicoAgendadoMapper {

    public ServicoMecanicoAgendadoResponseDTO toDTO(ServicoMecanicoAgendado servico) {
        ServicoMecanicoAgendadoResponseDTO dto = new ServicoMecanicoAgendadoResponseDTO();
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
