package com.devlu.futuresistema_cliente.entities;

import jakarta.persistence.*; // Certifique-se de usar jakarta.persistence

/**
 * Entidade que representa um cliente no sistema.
 *
 * <p>Esta classe define os atributos de um cliente, como ID, nome, email,
 * telefone, endereço e status.</p>
 */
@Entity
@Table(name = "tb_cliente")
public class Cliente {

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
     * Endereço de email do cliente. Deve ser único.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Número de telefone do cliente.
     */
    @Column(nullable = false)
    private String telefone;

    /**
     * Status do cliente (ATIVO, INATIVO, EXCLUIDO).
     * Armazenado como String no banco de dados.
     */
    @Enumerated(EnumType.STRING)
    private StatusCliente status; // Usando o enum StatusCliente

    /**
     * Endereço físico do cliente.
     * <p>Relacionamento um-para-um com a entidade {@link Endereco}.
     * {@code CascadeType.ALL} garante que operações (persistir, atualizar, remover)
     * no cliente também afetam o endereço associado.
     * {@code orphanRemoval = true} remove o endereço do banco se for desassociado do cliente.
     * {@code @JoinColumn(name = "endereco_id", unique = true)} cria a coluna de chave
     * estrangeira {@code endereco_id} na tabela {@code tb_cliente} e garante que cada
     * endereço seja associado a apenas um cliente.</p>
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", unique = true) // 'unique = true' garante o mapeamento One-to-One do lado do cliente
    private Endereco endereco;

    // Construtor padrão (necessário para JPA)
    public Cliente() {
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

    public StatusCliente getStatus() {
        return status;
    }

    public void setStatus(StatusCliente status) {
        this.status = status;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}