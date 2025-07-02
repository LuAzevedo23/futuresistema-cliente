package com.devlu.futuresistema_cliente.entities;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa o endereço de um cliente.
 * Define os atributos de um endereço, como CEP, logradouro, número, complemento, bairro, cidade e estado.
 * Não utiliza Lombok; getters, setters, equals e hashCode são explícitos.
 */
@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String logradouro;

    private Long numero; // Pode ser nulo

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    /**
     * Status do endereço.
     */
    private String status;

    // Construtor padrão (necessário para JPA)
    public Endereco() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco that = (Endereco) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(cep, that.cep) &&
                Objects.equals(logradouro, that.logradouro) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(complemento, that.complemento) &&
                Objects.equals(bairro, that.bairro) &&
                Objects.equals(cidade, that.cidade) &&
                Objects.equals(estado, that.estado) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cep, logradouro, numero, complemento, bairro, cidade, estado, status);
    }
}