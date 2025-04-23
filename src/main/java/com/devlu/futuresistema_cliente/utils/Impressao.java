package com.devlu.futuresistema_cliente.utils;

import javafx.application.Platform;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


public class Impressao {

    public static void imprimir(Node node, Scene scene) {
        Platform.runLater(() -> {
            if (scene == null) {
                Notificacao.exibirMensagem("Erro", "Erro ao Imprimir",
                        "A cena não está disponível.", Alert.AlertType.ERROR);
                return;
            }

            // Verificar se o nó não é nulo
            if (node == null) {
                Notificacao.exibirMensagem("Erro", "Erro ao Imprimir",
                        "O componente a ser impresso não está disponível.", Alert.AlertType.ERROR);
                return;
            }

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(scene.getWindow())) {
                // Opção 1: Imprimir o nó diretamente
                boolean success = job.printPage(node);

                /* Opção 2: Caso precisemos de um snapshot para ajustes
                SnapshotParameters params = new SnapshotParameters();
                params.setTransform(new Scale(1.0, 1.0)); // Ajustar a escala conforme necessário
                WritableImage snapshot = node.snapshot(params, null);

                // Converter a imagem para um Node (ImageView)
                ImageView imageView = new ImageView(snapshot);
                boolean success = job.printPage(imageView);
                */

                if (success) {
                    job.endJob();
                } else {
                    Notificacao.exibirMensagem("Erro", "Erro ao Imprimir",
                            "Ocorreu um erro ao imprimir o relatório.", Alert.AlertType.ERROR);
                }
            }
        });
    }
}
