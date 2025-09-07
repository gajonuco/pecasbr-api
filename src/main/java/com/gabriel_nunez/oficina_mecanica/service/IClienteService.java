package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public interface IClienteService {
    
    public Cliente buscarPeloTefone(String telefone);
    public Cliente atualizarDados(Cliente dadosOriginais);
    public Cliente buscarPeloCPF(String cpf);
}
