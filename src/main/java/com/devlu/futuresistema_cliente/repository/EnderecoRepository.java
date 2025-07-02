package com.devlu.futuresistema_cliente.repository;

import com.devlu.futuresistema_cliente.entities.Endereco; // Importando a entidade Endereco
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para acessar e manipular os dados dos endereços no banco de dados.
 *
 * <p>Esta interface estende {@link JpaRepository} para fornecer métodos CRUD
 * padrão para a entidade {@link Endereco}.</p>
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}