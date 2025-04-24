package com.gabriel_nunez.oficina_mecanica.util;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.CNPJValidator;

public class DocumentoUtils {

    public static String limparDocumento(String documento) {
        return documento.replaceAll("[^0-9]", "");
    }

    public static boolean validarDocumento(String documento) {
        String docLimpo = limparDocumento(documento);

        if (docLimpo.length() == 11) {
            return new CPFValidator().isEligible(docLimpo);
        } else if (docLimpo.length() == 14) {
            return new CNPJValidator().isEligible(docLimpo);
        }
        return false;
    }

    public static String formatarDocumento(String documento) {
        String docLimpo = limparDocumento(documento);

        if (docLimpo.length() == 11) {
            return docLimpo.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        } else if (docLimpo.length() == 14) {
            return docLimpo.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }
        throw new IllegalArgumentException("Documento inválido! CPF deve ter 11 dígitos e CNPJ deve ter 14.");
    }
}
