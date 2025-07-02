
package com.devlu.futuresistema_cliente.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.Valid; // Importe para validar objetos aninhados
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada
 * de uma requisição de criação ou atualização de cliente.
 * Contém validações para garantir a integridade dos dados recebidos.
 */
public class ClienteRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    // CORREÇÃO FINAL AQUI: TODAS as barras invertidas para regex estão DUPLICADAS
    // Explicação:
    // ^      : Início da string
    // \(?   : Parêntese de abertura opcional (o '\(' é o literal parêntese, e o '?' o torna opcional)
    // \d{2} : Dois dígitos (o '\d' é para dígito, e o '{2}' significa 2 ocorrências)
    // \)?   : Parêntese de fechamento opcional
    // \s*   : Zero ou mais caracteres de espaço em branco (o '\s' é para espaço em branco)
    // \d{4,5}: Quatro ou cinco dígitos
    // -?     : Hífen opcional
    // \d{4} : Quatro dígitos
    // $      : Fim da string
    @Pattern(regexp = "^/(?/d{2}/')'?/s*/d{4,5}-?/d{4}$", message =
            "Formato de telefone inválido. Ex: (DD) XXXXX-XXXX")
    private String telefone;

    @NotBlank(message = "O status é obrigatório.")
    @Pattern(regexp = "ATIVO|INATIVO|EXCLUIDO", message =
            "Status inválido. Use ATIVO, INATIVO ou EXCLUIDO.")
    private String status;

    @Valid // Valida o objeto EnderecoRequestDTO aninhado
    @NotNull(message = "Os dados de endereço são obrigatórios.")
    private EnderecoRequestDTO endereco;

    // Construtor padrão
    public ClienteRequestDTO() {
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }
}