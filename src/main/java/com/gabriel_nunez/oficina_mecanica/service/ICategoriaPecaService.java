package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;

public interface ICategoriaPecaService {

    //Este método vai receber uma categoria das peças mecânicas só com o nome preenchido e vai inserir no banco
    public CategoriaPeca adicionarNovaCategoriaPeca (CategoriaPeca categoriaPeca);

    //Este método vai alterar a categoria das peças mecânicas e retorna-la se o update deu certo e null caso contrário

    public CategoriaPeca alterarCategoriaPeca(CategoriaPeca categoriaPeca);

    //Este método vai recuperar todas as categorias sem filtro
    public ArrayList<CategoriaPeca> recuperarTodasCategoriasPecas();

    //Este método vai recuperar todas as categorias por palvra chave
    public ArrayList<CategoriaPeca> recuperarPorPalavraChave(String palavraChave);
} 
