package com.gabriel_nunez.oficina_mecanica.service;


import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

public interface IPecaService {

    public Peca            inserirNovaPeca(Peca peca);
    public Peca            alterarPeca(Peca peca);
    public Peca            recuperarPorId(int idPeca);
    public ArrayList<Peca> listarTodos();
    public ArrayList<Peca> listarDisponiveis();
    public ArrayList<Peca> listarDestaques();
    public ArrayList<Peca> listarIndisponiveis();
    public ArrayList<Peca> listarPorCategoria(CategoriaPeca categoriaPeca);
    public ArrayList<Peca> listarPecaPorPalavraChave(String palavraChave);

    
}
