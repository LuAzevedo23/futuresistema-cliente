package com.devlu.futuresistema_cliente.repository;

import com.devlu.futuresistema_cliente.entities.Cliente; // Importando a entidade Cliente
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importando Optional para o método findByEmail

/**
 * Repositório para acessar e manipular os dados dos clientes no banco de dados.
 *
 * <p>Esta interface estende {@link JpaRepository} para fornecer métodos CRUD
 * (Create, Read, Update, Delete) padrão para a entidade {@link Cliente}.</p>
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente pelo seu endereço de e-mail.
     *
     * @param email O endereço de e-mail do cliente a ser buscado.
     * @return Um {@link Optional} contendo o {@link Cliente} se encontrado,
     *         ou um {@link Optional#empty()} caso contrário.
     */
    Optional<Cliente> findByEmail(String email);
}