package com.gabriel_nunez.oficina_mecanica.mapper;


import com.gabriel_nunez.oficina_mecanica.dto.RegisterDTO;
import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import com.gabriel_nunez.oficina_mecanica.user.User;

import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromRegisterDTO(RegisterDTO dto, Endereco endereco, String senhaCriptografada) {
        return new User(
            null,
            dto.login(),
            senhaCriptografada,
            dto.role(),
            dto.nome(),
            DocumentoUtils.formatarDocumento(dto.cpfCnpj()),
            dto.telefone(),
            endereco

        );
    }
    
    
}
