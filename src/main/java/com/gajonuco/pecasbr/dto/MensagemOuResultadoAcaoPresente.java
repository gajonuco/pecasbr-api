package com.gajonuco.pecasbr.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MensagemOuResultadoAcaoPresenteValidator.class)
public @interface MensagemOuResultadoAcaoPresente {
    String message() default "A requisição deve conter 'mensagem' ou 'resultadoAcao'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}