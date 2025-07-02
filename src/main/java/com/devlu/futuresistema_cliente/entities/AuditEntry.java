package com.devlu.futuresistema_cliente.entities;

import jakarta.persistence.*; // Certifique-se de usar jakarta.persistence
import java.time.LocalDateTime; // Para registrar a data e hora da auditoria

/**
 * Entidade de auditoria para registrar operações de criação, atualização e exclusão
 * em outras entidades (como Cliente e Endereco).
 */
@Entity
@Table(name = "tb_audit_entry") // Nome da tabela no banco de dados para os registros de auditoria
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityType; // Tipo da entidade auditada (Ex: "Cliente", "Endereco")

    @Column(nullable = false)
    private Long entityId; // ID da entidade afetada pela operação

    @Column(nullable = false)
    private String operationType; // Tipo da operação (Ex: "INSERT", "UPDATE", "DELETE")

    @Column(nullable = false)
    private LocalDateTime timestamp; // Data e hora em que a operação ocorreu

    @Column(length = 2000) // Detalhes da alteração (pode ser JSON, String descritiva, etc.)
    private String details; // Ex: "Campo 'nome' alterado de 'Antigo' para 'Novo'"

    // Construtor padrão (necessário para JPA)
    public AuditEntry() {
    }

    /**
     * Construtor para criar uma nova entrada de auditoria.
     * O timestamp é automaticamente preenchido com a data e hora atual.
     *
     * @param entityType O tipo da entidade que foi auditada (ex: "Cliente", "Endereco").
     * @param entityId O ID da instância da entidade auditada.
     * @param operationType O tipo de operação realizada (ex: "INSERT", "UPDATE", "DELETE").
     * @param details Uma descrição detalhada da operação ou das mudanças.
     */
    public AuditEntry(String entityType, Long entityId, String operationType, String details) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.operationType = operationType;
        this.timestamp = LocalDateTime.now(); // Registra o momento da criação da entrada de auditoria
        this.details = details;
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Retorna uma representação em String desta entrada de auditoria.
     * Útil para logs ou depuração.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s (%s ID: %d): %s",
                timestamp.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                operationType, entityType, entityId, details);
    }
}
