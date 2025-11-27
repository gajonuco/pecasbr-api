package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.PecaDAO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

@Component
public class PecaServiceImpl implements IPecaService {
    
    public static final int PAGE_SIZE=18;
    @Autowired
    private PecaDAO dao;

    @Override
    public Peca inserirNovaPeca(Peca peca) {
        // TODO Auto-generated method stub
       try {
            dao.save(peca);
            return peca;
       } catch (Exception ex) {
        System.out.println("---- PecaService.inserirNovaPeca()-----");
            ex.printStackTrace();
        System.out.println("-------------------------------------");
       }
       return null;
    }

    @Override
    public Peca alterarPeca(Peca peca) {
        // TODO Auto-generated method stub
        try {
            dao.save(peca);
            return peca;
       } catch (Exception ex) {
        System.out.println("---- PecaService.aletrarPeca()-----");
            ex.printStackTrace();
        System.out.println("-------------------------------------");
       }
       return null;
    }
    

    @Override
    public ArrayList<Peca> listarTodos() {
        // TODO Auto-generated method stub
        return (ArrayList<Peca>)dao.findAll();
    }


    @Override
	public Page<Peca> listarDisponiveis(int pagina) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pagina, PAGE_SIZE);
		
		return dao.findAllByDisponivel(1, pageable); // Considero todas as peças '1' como disponíveis
		
	}

    @Override
    public ArrayList<Peca> listarPorCategoria(CategoriaPeca categoriaPeca) {
        // TODO Auto-generated method stub
        return dao.findAllByCategoriaPecaAndDisponivel( categoriaPeca,1);
    }

	@Override
	public ArrayList<Peca> listarIndisponiveis() {
		// TODO Auto-generated method stub
		return (ArrayList<Peca>)dao.findAllByDisponivel(0, null).toList();
	}

    @Override
    public Peca recuperarPorId(int idPeca) {
        // TODO Auto-generated method stub
        return dao.findById(idPeca).orElse(null);
    }

	@Override
	public Page<Peca> listarDestaques(int pagina) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pagina, PAGE_SIZE);
		
		
		return dao.findAllByDestaqueAndDisponivel(1,1, pageable);
	}

	@Override
	public Page<Peca> listarPorPalavraChave(String palavraChave, int pagina) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pagina, PAGE_SIZE);
		return dao.findAllByDisponivelAndNomeContainingOrDisponivelAndDetalheContaining(1, palavraChave, 1, palavraChave, pageable);
	}

}
