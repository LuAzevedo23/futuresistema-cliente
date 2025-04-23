package com.devlu.futuresistema_cliente;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


// Classe para rodar Spring e lançar JavaFX
public class AppLauncher extends Application {

    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder(FuturesistemaClienteApplication.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Criando componentes da interface
            javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("Future Sistema - Cliente");

            javafx.scene.control.Label statusLabel = new javafx.scene.control.Label("Sistema conectado com sucesso!");

            javafx.scene.control.Button clientesButton = new javafx.scene.control.Button("Gerenciar Clientes");
            clientesButton.setOnAction(e -> {
                try {
                    openClientesView();
                } catch (Exception ex) {
                    showErrorDialog("Erro ao abrir gerenciador de clientes", ex);
                }
            });

            javafx.scene.control.Button configButton = new javafx.scene.control.Button("Configurações");
            configButton.setOnAction(e -> {
                statusLabel.setText("Configurações ainda não implementadas");
            });

            // Layout principal
            javafx.scene.layout.VBox mainLayout = new javafx.scene.layout.VBox(15);
            mainLayout.setPadding(new Insets(20));
            mainLayout.setAlignment(Pos.TOP_CENTER);

            // Área para botões
            javafx.scene.layout.HBox buttonsLayout = new javafx.scene.layout.HBox(20);
            buttonsLayout.setAlignment(Pos.CENTER);
            buttonsLayout.getChildren().add(clientesButton);
            buttonsLayout.getChildren().add(configButton);

            // Adicionando individualmente, em vez de usar addAll
            mainLayout.getChildren().add(titleLabel);
            mainLayout.getChildren().add(statusLabel);
            mainLayout.getChildren().add(buttonsLayout);

            // Mostrando a cena
            Scene scene = new Scene(mainLayout, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Future Sistema");
            primaryStage.show();

            System.out.println("JavaFX UI iniciada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar a interface JavaFX: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openClientesView() {
        // Método que seria chamado para abrir a tela de clientes
        System.out.println("Abrindo gerenciador de clientes...");

        // Por enquanto, apenas mostra um alerta
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Clientes");
        alert.setHeaderText("Gerenciador de Clientes");
        alert.setContentText("Esta funcionalidade será implementada em breve!");
        alert.showAndWait();
    }

    private void showErrorDialog(String message, Exception ex) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(message);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }
}