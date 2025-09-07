package com.gabriel_nunez.oficina_mecanica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.ClienteDAO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;

@Component
public class ClienteServiceImpl implements IClienteService{
    
    @Autowired
    private ClienteDAO dao;

    @Override
    public Cliente buscarPeloCPF(String cpf) {
        /*if(cpf.charAt(0) == '0'){
            cpf = cpf.substring(1);
        }*/
         return dao.findByCpf(cpf);
    }

    @Override
    public Cliente atualizarDados(Cliente dadosOriginais) {
        // TODO Auto-generated method stub
       return dao.save(dadosOriginais);
    }

    @Override
    public Cliente buscarPeloTefone(String telefone) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPeloTefone'");
    }
}
