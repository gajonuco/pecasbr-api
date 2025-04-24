package com.gabriel_nunez.oficina_mecanica.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDatabaseException(org.springframework.dao.DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar cliente: CPF/CNPJ já cadastrado.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String campo = error.getField();
            String mensagem = error.getDefaultMessage();

            if (campo.equals("placa")) {
                mensagem = "A placa deve seguir o padrão Mercosul (ex: ABC1D23)";
            }

            errors.put(campo, mensagem);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleEmptyBodyException(HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        String message = ex.getMessage();
    
        if (message != null) {
            if (message.contains("Unexpected character")) {
                response.put("erro", "Erro de formatação no JSON: verifique os campos enviados. Exemplo válido: \"ano\": 2020");
            } else if (message.contains("Illegal unquoted character")) {
                response.put("erro", "Erro de formatação: alguns campos contêm caracteres inválidos. Certifique-se de que os valores estejam entre aspas corretamente e que não haja quebras de linha ou símbolos invisíveis.");
            } else {
                response.put("erro", "Erro de leitura do corpo da requisição: verifique o JSON enviado.");
            }
        } else {
            response.put("erro", "O corpo da requisição está vazio. Você deve enviar os campos obrigatórios.");
        }
    
        return ResponseEntity.badRequest().body(response);
    }
    
    

    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String campo = violation.getPropertyPath().toString();
            String mensagem = violation.getMessage();

            if (campo.contains("placa")) {
                mensagem = "A placa deve seguir o padrão Mercosul (ex: ABC1D23)";
            }

            errors.put(campo, mensagem);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DuplicatePlacaException.class)
    public ResponseEntity<Map<String, String>> handleDuplicatePlacaException(DuplicatePlacaException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("erro", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}

}
