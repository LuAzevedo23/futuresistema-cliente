package com.devlu.futuresistema_cliente.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que define os campos necessários para cadastrar ou atualizar um endereço.
 *
 * <p>Esta classe utiliza anotações de validação para garantir que os dados recebidos sejam válidos.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDTO {

    /**
     * CEP do endereço.
     */
    @NotEmpty(message = "O CEP não pode estar vazio")
    @Size(min = 8, max = 9, message = "O CEP deve ter 8 ou 9 caracteres")
    private String cep;

    /**
     * Logradouro do endereço.
     */
    @NotEmpty(message = "O logradouro não pode estar vazio")
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
    @NotEmpty(message = "O bairro não pode estar vazio")
    private String bairro;

    /**
     * Cidade do endereço.
     */
    @NotEmpty(message = "A cidade não pode estar vazia")
    private String cidade;

    /**
     * Estado do endereço.
     */
    @NotEmpty(message = "O estado não pode estar vazio")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
    private String estado;

    /**
     * Status do endereço.
     */
    private String status;
}