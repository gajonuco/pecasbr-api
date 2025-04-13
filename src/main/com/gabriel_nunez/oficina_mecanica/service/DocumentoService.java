package com.gabriel_nunez.oficina_mecanica.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.CNPJValidator;
import org.springframework.stereotype.Service;

@Service
public class DocumentoService {

    public String limpar(String documento) {
        return documento.replaceAll("[^0-9]", "");
    }

    public boolean validar(String documento) {
        String doc = limpar(documento);
        if (doc.length() == 11) return new CPFValidator().isEligible(doc);
        if (doc.length() == 14) return new CNPJValidator().isEligible(doc);
        return false;
    }

    public String formatar(String documento) {
        String doc = limpar(documento);
        if (doc.length() == 11) return doc.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        if (doc.length() == 14) return doc.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        throw new IllegalArgumentException("Documento inválido!");
    }
}
