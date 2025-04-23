package com.devlu.futuresistema_cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que define os campos que serão retornados na resposta da API.
 *
 * <p>Esta classe encapsula os dados do cliente que serão enviados como resposta para as requisições.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

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
    private EnderecoDTO endereco;


}

