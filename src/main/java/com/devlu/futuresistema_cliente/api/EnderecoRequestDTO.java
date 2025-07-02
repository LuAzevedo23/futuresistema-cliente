package com.devlu.futuresistema_cliente.api;

import com.devlu.futuresistema_cliente.entities.StatusEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada
 * de uma requisição de endereço.
 */
public class EnderecoRequestDTO {

    // O ID pode ser nulo se for um novo endereço, ou preenchido para atualização
    private Long id;

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "^/d{5}-?/d{3}$", message = "Formato de CEP inválido. Use XXXXX-XXX ou XXXXXXXX.")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório.")
    private String logradouro;

    /**
     * Número do endereço. Pode ser nulo.
     */
    private Long numero;

    /**
     * Complemento do endereço. Pode ser nulo.
     */
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    /**
     * Status do endereço (ATIVO, INATIVO, EXCLUIDO).
     */
    @NotBlank(message = "O status do endereço é obrigatório.")
    @Pattern(regexp = "ATIVO|INATIVO|EXCLUIDO", message = "Status inválido. Use ATIVO, INATIVO ou EXCLUIDO.")
    private String status; // AGORA PRESENTE!

    // Construtor padrão
    public EnderecoRequestDTO() {}

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getStatus() { return status; } // NOVO: Getter para o status
    public void setStatus(String status) { this.status = status; } // NOVO: Setter para o status
}