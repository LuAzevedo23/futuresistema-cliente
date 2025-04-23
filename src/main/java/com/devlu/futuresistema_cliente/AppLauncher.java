package com.devlu.futuresistema_cliente;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        // Inicializa o contexto Spring Boot
        springContext = new SpringApplicationBuilder(FuturesistemaClienteApplication.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Título
            Label titleLabel = new Label("Future Sistema - Cliente");
            titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

            // Status
            Label statusLabel = new Label("Sistema conectado com sucesso!");
            statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green;");

            // Botão Gerenciar Clientes
            Button clientesButton = new Button("Gerenciar Clientes");
            clientesButton.setOnAction(e -> {
                try {
                    openClientesView();
                } catch (Exception ex) {
                    showErrorDialog("Erro ao abrir gerenciador de clientes", ex);
                }
            });

            // Botão Configurações
            Button configButton = new Button("Configurações");
            configButton.setOnAction(e -> {
                statusLabel.setText("Configurações ainda não implementadas");
            });

            // Layout de botões
            HBox buttonsLayout = new HBox(20, clientesButton, configButton);
            buttonsLayout.setAlignment(Pos.CENTER);

            // Layout principal
            VBox mainLayout = new VBox(24, titleLabel, statusLabel, buttonsLayout);
            mainLayout.setPadding(new Insets(30));
            mainLayout.setAlignment(Pos.TOP_CENTER);

            // Cena principal
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

    // Abre o FXML do Gerenciador de Clientes em uma nova janela
    private void openClientesView() {
        System.out.println("Abrindo gerenciador de clientes...");

        try {
            // Garante que encontra o FXML conforme a pasta do projeto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GerenciadorClientes.fxml"));

            // (Opcional) debug: veja se está encontrando o arquivo!
            System.out.println("URL do FXML = " + getClass().getResource("/view/GerenciadorClientes.fxml"));

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gerenciador de Clientes");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorDialog("Erro ao abrir gerenciador de clientes", ex);
        }
    }

    // Alerta de erro genérico
    private void showErrorDialog(String message, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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