package com.devlu.futuresistema_cliente.controller;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;

@Component
public class MySpringController {

    // Autowire qualquer serviço do Spring aqui

    public Parent loadView() {
        // Pode carregar um FXML ou montar nodes manualmente
        // exemplo: FXMLLoader loader = new FXMLLoader(resource);
        // loader.setControllerFactory(springContext::getBean);
        // return loader.load();
        // Para exemplo, retorna um Component simples:
        return new BorderPane();
    }
}

