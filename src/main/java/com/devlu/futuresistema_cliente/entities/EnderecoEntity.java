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
@Data
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

    /**
     * Estado do endereço.
     */
    @Column(nullable = false)
    private String estado;

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



