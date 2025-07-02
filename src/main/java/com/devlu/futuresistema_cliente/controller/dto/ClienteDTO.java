package com.devlu.futuresistema_cliente.controller.dto;

/**
 * Classe DTO (Data Transfer Object) para transferir dados de clientes.
 * <p>
 * Esta classe é utilizada para representar os dados de um cliente que serão
 * transferidos entre as camadas da aplicação, principalmente para a interface do usuário.
 */
public class ClienteDTO {

    private Long id; // Identificador único do cliente (opcional para criação)
    private String nome;
    private String email;
    private String telefone;
    private String status; // Representa o StatusCliente (ATIVO, INATIVO, EXCLUIDO) como String
    private EnderecoDTO endereco; // Objeto DTO que representa o endereço do cliente

    // Construtor padrão
    public ClienteDTO() {
    }

    // Getters e Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}