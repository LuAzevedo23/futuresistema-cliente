package com.devlu.futuresistema_cliente.controller.controllerFX.callbacks;

/**
 * Interface de callback para comunicação entre controladores JavaFX,
 * especialmente para notificar sobre a necessidade de recarregar a lista de clientes
 * e mudar para a aba de listagem de clientes.
 */
public interface ClientManagementCallback {
    /**
     * Notifica que a lista de clientes precisa ser recarregada.
     */
    void refreshClientList();

    /**
     * Notifica que a aba da lista de clientes deve ser selecionada.
     */
    void selectClientListTab();
}