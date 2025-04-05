package com.devlu.futuresistema_cliente.repository;

import com.devlu.futuresistema_cliente.entities.ClienteEntity;
import com.devlu.futuresistema_cliente.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface para acessar e manipular os dados dos endereços no banco de dados.
 *
 * <p>Esta interface estende {@link JpaRepository} para fornecer métodos CRUD padrão.</p>
 */
@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity,Long>{

}
