package com.gabriel_nunez.oficina_mecanica.service;


import java.util.ArrayList;
import org.springframework.data.domain.Page;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

public interface IPecaService {

    public Peca            inserirNovaPeca(Peca peca);
    public Peca            alterarPeca(Peca peca);
    public Peca            recuperarPorId(int idPeca);
    public ArrayList<Peca> listarTodos();
	public Page<Peca> 	  listarDisponiveis(int pagina);
	public Page<Peca> 	  listarDestaques(int pagina);
    public ArrayList<Peca> listarIndisponiveis();
    public ArrayList<Peca> listarPorCategoria(CategoriaPeca categoriaPeca);
    public Page<Peca>      listarPorPalavraChave(String palavraChave, int pagina);


    
}
