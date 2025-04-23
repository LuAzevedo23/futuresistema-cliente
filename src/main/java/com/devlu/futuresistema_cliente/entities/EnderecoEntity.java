package com.devlu.futuresistema_cliente.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Entidade que representa o endereço de um cliente.
 *
 * <p>Esta classe define os atributos de um endereço, como CEP, logradouro, número, complemento, bairro e cidade.</p>
 */
@Entity
@Table(name = "tb_endereco")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {

    /**
     * Identificador único do endereço.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * CEP do endereço.
     */
    @Column(nullable = false)
    private String cep;

    /**
     * Logradouro do endereço.
     */
    @Column(nullable = false)
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
    @Column(nullable = false)
    private String bairro;

    /**
     * Cidade do endereço.
     */
    @Column(nullable = false)
    private String cidade;

    private String status;

    /**
     * Estado do endereço.
     */
    @Column(nullable = false)
    private String estado;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Implementação do método equals para comparar objetos {@link EnderecoEntity} com base em todos os atributos.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnderecoEntity that = (EnderecoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(cep, that.cep) &&
                Objects.equals(logradouro, that.logradouro) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(complemento, that.complemento) &&
                Objects.equals(bairro, that.bairro) &&
                Objects.equals(cidade, that.cidade) &&
                Objects.equals(status, that.status) &&
                Objects.equals(estado, that.estado);
    }

    /**
     * Implementação do método hashCode para gerar um código hash com base em todos os atributos.
     *
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, cep, logradouro, numero, complemento, bairro, cidade, estado);
    }
}



