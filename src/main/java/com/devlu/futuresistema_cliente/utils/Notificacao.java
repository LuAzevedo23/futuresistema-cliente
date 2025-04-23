package com.devlu.futuresistema_cliente.utils;

import javafx.scene.control.Alert;

public class Notificacao {

    public static void exibirMensagem(String titulo, String cabecalho, String conteudo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}
