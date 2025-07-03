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

    private String cep;
    private String logradouro;
    private Long numero; // Se for String para S/N, mude para String e remova o NumberFormatException na UI
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    // --- AJUSTE CRUCIAL AQUI: O TIPO DO ATRIBUTO 'status' DEVE SER UM ENUM ---
    @Enumerated(EnumType.STRING) // Permite que o Enum seja salvo como String no banco
    @Column(nullable = false) // Opcional: Se o status não puder ser nulo no banco
    private StatusEndereco status = StatusEndereco.ATIVO; // <--- AJUSTE 1: Tipo mudou para StatusEndereco
    // <--- AJUSTE 2: Valor padrão ATIVO para novos registros

    // Construtor vazio (necessário para JPA)
    public Endereco() {
    }

    // Construtor com todos os campos (opcional, mas útil)
    public Endereco(String cep, String logradouro, Long numero, String complemento, String bairro, String cidade, String estado, StatusEndereco status) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.status = status;
    }

    // Getters e Setters
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

    public StatusEndereco getStatus() { // <--- AJUSTE: Retorna StatusEndereco
        return status;
    }

    public void setStatus(StatusEndereco status) { // <--- AJUSTE: Recebe StatusEndereco
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