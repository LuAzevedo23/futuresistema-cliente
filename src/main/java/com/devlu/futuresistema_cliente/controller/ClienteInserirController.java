package com.devlu.futuresistema_cliente.controller;

import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ClienteInserirController {

    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telefoneTextField;

    @FXML
    public void initialize() {
        // Inicializações necessárias
    }

    @FXML
    private void inserirCliente() {
        // Lógica para inserir o cliente
        String nome = nomeTextField.getText();
        String email = emailTextField.getText();
        String telefone = telefoneTextField.getText();

        // Chamar o serviço Spring Boot para inserir o cliente
        boolean inseridoComSucesso = true; // Substituir pela chamada real ao serviço

        Notificacao.exibirMensagem("Sucesso", "Cliente Inserido", "O cliente foi inserido com sucesso!", Alert.AlertType.INFORMATION);
    }
}
