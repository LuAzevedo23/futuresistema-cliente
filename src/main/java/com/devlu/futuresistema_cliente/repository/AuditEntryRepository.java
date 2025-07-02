package com.devlu.futuresistema_cliente.repository;

import com.devlu.futuresistema_cliente.entities.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para acessar e manipular os dados de auditoria no banco de dados.
 *
 * <p>Esta interface estende {@link JpaRepository} para fornecer métodos CRUD
 * padrão para a entidade {@link AuditEntry}.</p>
 */
@Repository
public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {

    /**
     * Busca todas as entradas de auditoria para uma entidade específica,
     * ordenadas pela data e hora de registro em ordem crescente.
     *
     * @param entityType O tipo da entidade (ex: "Cliente", "Endereco").
     * @param entityId O ID da entidade auditada.
     * @return Uma lista de {@link AuditEntry} correspondentes.
     */
    List<AuditEntry> findByEntityTypeAndEntityIdOrderByTimestampAsc(String entityType, Long entityId);
}