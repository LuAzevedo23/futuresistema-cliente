package com.devlu.futuresistema_cliente.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Classe utilitária para exibir notificações e mensagens de alerta na interface JavaFX.
 * Centraliza a lógica de exibição de diálogos para manter a consistência visual e de comportamento.
 */
public class Notificacao {

    /**
     * Exibe uma mensagem de alerta simples ao usuário.
     *
     * @param title O título da janela de alerta.
     * @param header O cabeçalho da mensagem (geralmente uma breve descrição).
     * @param content O conteúdo detalhado da mensagem.
     * @param alertType O tipo de alerta (e.g., INFORMATION, WARNING, ERROR).
     */
    public static void exibirMensagem(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * ADIÇÃO: Exibe uma caixa de diálogo de confirmação e retorna a escolha do usuário.
     *
     * @param title O título da janela de confirmação.
     * @param header O cabeçalho da mensagem (geralmente uma breve descrição).
     * @param content O conteúdo detalhado da mensagem de confirmação.
     * @return Um Optional contendo o ButtonType clicado pelo usuário (e.g., OK, CANCEL).
     */
    public static Optional<ButtonType> exibirConfirmacao(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}

