package com.devlu.futuresistema_cliente.repository;

import com.devlu.futuresistema_cliente.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para acessar e manipular os dados dos clientes no banco de dados.
 *
 * <p>Esta interface estende {@link JpaRepository} para fornecer métodos CRUD para a entidade {@link ClienteEntity}.</p>
 */
@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,Long>{

}
