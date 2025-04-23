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
    private EnderecoDTO endereco;

    public ClienteDTO(){

    }

    public ClienteDTO(Long id, String nome, String email, String telefone, EnderecoDTO endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}

