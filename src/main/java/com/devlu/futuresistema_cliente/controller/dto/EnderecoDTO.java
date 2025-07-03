package com.devlu.futuresistema_cliente.controller.dto;

import com.devlu.futuresistema_cliente.entities.Endereco;
import com.devlu.futuresistema_cliente.entities.StatusEndereco; // <--- AJUSTE: Importe o StatusEndereco

/**
 * Classe DTO (Data Transfer Object) para transferir dados de endereços.
 * Não utiliza Lombok; getters e setters são explícitos.
 */
public class EnderecoDTO {

    private Long id;
    private String cep;
    private String logradouro;
    private Long numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String status; // <--- OK: O status no DTO DEVE ser String para a UI

    // Construtor padrão
    public EnderecoDTO() {
    }

    // Construtor que recebe um objeto EnderecoEntity e preenche os dados do DTO.
    public EnderecoDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        // --- AJUSTE DIDÁTICO: Converte o Enum da entidade para String do DTO ---
        // Aqui, convertemos o StatusEndereco (enum) para String (.name()) para que o DTO
        // possa ser usado na UI (JavaFX) que espera Strings.
        this.status = (endereco.getStatus() != null) ? endereco.getStatus().name() : null; // <--- AJUSTE
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}