package com.devlu.futuresistema_cliente.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que define os campos necessários para cadastrar ou atualizar um cliente.
 *
 * <p>Esta classe utiliza anotações de validação para garantir que os dados recebidos sejam válidos.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    /**
     * Nome completo do cliente.
     */
    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
    private String nome;

    /**
     * Endereço de email do cliente.
     */
    @NotEmpty(message = "O email não pode estar vazio")
    @Email(message = "Email inválido")
    private String email;

    /**
     * Número de telefone do cliente.
     */
    @NotEmpty(message = "O telefone não pode estar vazio")
    @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres")
    private String telefone;

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
}