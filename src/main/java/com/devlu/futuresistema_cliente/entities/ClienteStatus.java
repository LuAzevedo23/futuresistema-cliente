package com.devlu.futuresistema_cliente.entities;

/**
 * Enumeração que define os possíveis status de um cliente.
 *
 * <p>Os status possíveis são ATIVO, INATIVO e EXCLUIDO.</p>
 */

public enum ClienteStatus {

    /**
     * ClienteEntity ativo no sistema.
     */
    ATIVO,
    /**
     * ClienteEntity inativo no sistema.
     */
    INATIVO,
    /**
     * ClienteEntity excluído do sistema.
     */
    EXCLUIDO

}
