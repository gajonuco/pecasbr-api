package com.gabriel_nunez.oficina_mecanica.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private Object endereco;
    private List<VeiculoResponseDTO> veiculos; // Agora corretamente tipado
}
