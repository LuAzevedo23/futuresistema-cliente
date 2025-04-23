package com.devlu.futuresistema_cliente.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Entidade que representa um cliente no sistema.
 *
 * <p>Esta classe define os atributos de um cliente, como ID, nome, email, telefone, endereço e status.</p>
 */
@Entity
@Table(name = "tb_cliente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {

    /**
     * Identificador único do cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do cliente.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Endereço de email do cliente.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Número de telefone do cliente.
     */
    @Column(nullable = false)
    private String telefone;

    /**
     * Endereço físico do cliente.
     *
     * <p>Relacionamento com a entidade {@link EnderecoEntity}.</p>
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    /**
     * Status do cliente (ATIVO, INATIVO, EXCLUIDO).
     */
    @Enumerated(EnumType.STRING)
    private ClienteStatus status;

    /**
     * Implementação do método equals para comparar objetos {@link ClienteEntity} com base em todos os atributos.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(endereco, that.endereco) &&
                status == that.status;
    }

    /**
     * Implementação do método hashCode para gerar um código hash com base em todos os atributos.
     *
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {

        return Objects.hash(id, nome, email, telefone, endereco, status);
    }
}