package com.gabriel_nunez.oficina_mecanica.dto.response;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Builder
@Getter
@Setter
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
