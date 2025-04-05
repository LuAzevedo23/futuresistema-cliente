package com.devlu.futuresistema_cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe que define as mensagens de erro para campos específicos.
 *
 * <p>Esta classe encapsula o nome do campo e a mensagem de erro associada.</p>
 */
@Getter
@AllArgsConstructor
public class FieldMessage {

    /**
     * Nome do campo que gerou o erro.
     */
    private String fieldName;

    /**
     * Mensagem de erro para o campo.
     */
    private String message;
}
