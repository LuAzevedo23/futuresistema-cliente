package com.devlu.futuresistema_cliente.controller.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que define a estrutura de erros de validação, estendendo {@link CustomError}.
 *
 * <p>Esta classe encapsula informações sobre o timestamp, status, mensagem de erro, caminho da requisição e uma lista
 * de mensagens de erro para campos específicos.</p>
 */
@Getter
public class ValidationError extends CustomError {

    /**
     * Lista de mensagens de erro para campos específicos.
     */
    private List<FieldMessage> errors = new ArrayList<>();

    /**
     * Construtor que recebe o timestamp, status, mensagem de erro e caminho da requisição.
     *
     * @param timestamp O timestamp do momento em que o erro ocorreu.
     * @param status    O código de status HTTP do erro.
     * @param error     A mensagem detalhada do erro.
     * @param path      O caminho da requisição que gerou o erro.
     */
    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    /**
     * Adiciona uma mensagem de erro para um campo específico à lista de erros.
     *
     * @param fieldName O nome do campo que gerou o erro.
     * @param message   A mensagem de erro para o campo.
     */
    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}

