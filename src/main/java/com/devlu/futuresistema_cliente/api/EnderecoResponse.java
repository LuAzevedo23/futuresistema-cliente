package com.devlu.futuresistema_cliente.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que define os campos que serão retornados na resposta da API para a entidade {@link com.futuresistema.cliente.infrastructure.entities.EnderecoEntity}.
 *
 * <p>Esta classe encapsula os dados do endereço que serão enviados como resposta.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponse {

    /**
     * Identificador único do endereço.
     */
    private Long id;

    /**
     * CEP do endereço.
     */
    private String cep;

    /**
     * Logradouro do endereço.
     */
    private String logradouro;

    /**
     * Número do endereço.
     */
    private String numero;

    /**
     * Complemento do endereço.
     */
    private String complemento;

    /**
     * Bairro do endereço.
     */
    private String bairro;

    /**
     * Cidade do endereço.
     */
    private String cidade;

    /**
     * Estado do endereço.
     */
    private String estado;

    /**
     * Status do endereço.
     */
    private String status;
}
