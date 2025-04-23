package com.devlu.futuresistema_cliente.config;

import com.devlu.futuresistema_cliente.controller.controllerFX.GerenciadorClientesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLoader {

    public void loadFXML(Stage stage) {
        try {
            // Carregar o FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/view/GerenciadorClientes.fxml"));
            Parent root = loader.load();

            // Obter o controller correto
            GerenciadorClientesController controller = loader.getController();

            // Criar a cena
            Scene scene = new Scene(root);

            // Definir a cena no palco (stage)
            stage.setScene(scene);
            stage.setTitle("Gerenciador de Clientes");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
