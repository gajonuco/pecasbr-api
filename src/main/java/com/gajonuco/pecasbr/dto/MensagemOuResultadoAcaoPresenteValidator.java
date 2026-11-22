package com.gajonuco.pecasbr.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MensagemOuResultadoAcaoPresenteValidator implements ConstraintValidator<MensagemOuResultadoAcaoPresente,AgenteMensagemRequest> {


    @Override
    public boolean isValid(AgenteMensagemRequest request, ConstraintValidatorContext context) {
        if(request == null){
            return true;
        }

        boolean temMensagem = request.mensagem() != null && !request.mensagem().isBlank();
        boolean temResultadoAcao = request.resultadoAcao() != null;

        return temMensagem || temResultadoAcao;
    }
}
