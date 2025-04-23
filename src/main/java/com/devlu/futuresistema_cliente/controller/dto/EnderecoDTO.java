package com.devlu.futuresistema_cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de transferência de dados (DTO) para a entidade {@link com.futuresistema.cliente.infrastructure.entities.EnderecoEntity}.
 *
 * <p>Esta classe define os campos necessários para transferir dados entre as camadas da aplicação.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

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
    private Long numero;

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

    private String status;

    public EnderecoDTO(String logradouro, String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}