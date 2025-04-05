package com.devlu.futuresistema_cliente.controller.dto;

import lombok.Data;

/**
 * Classe que define os campos que serão retornados na resposta da API.
 *
 * <p>Esta classe encapsula os dados do cliente que serão enviados como resposta para as requisições.</p>
 */
@Data
public class ClienteDTO {

    /**
     * Identificador único do cliente.
     */
    private Long id;

    /**
     * Nome completo do cliente.
     */
    private String nome;

    /**
     * Endereço de email do cliente.
     */
    private String email;

    /**
     * Número de telefone do cliente.
     */
    private String telefone;

    /**
     * Endereço físico do cliente.
     */
    private String endereco;
}

