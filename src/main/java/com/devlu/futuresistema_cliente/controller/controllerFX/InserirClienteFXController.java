package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.api.ClienteRequestDTO;
import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.application.Platform;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class InserirClienteFXController implements Initializable {

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField telefoneTextField;

    @FXML
    private Label mensagemLabel;

    @Autowired
    private ClienteService clienteService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void inserirCliente() {
        String nome = nomeTextField.getText();
        String email = emailTextField.getText();
        String telefone = telefoneTextField.getText();

        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty() || telefone == null || telefone.trim().isEmpty()) {
            Notificacao.exibirMensagem("Erro", "Dados Inválidos",
                    "Por favor, preencha todos os campos para inserir o cliente.", Alert.AlertType.ERROR);
            return;
        }

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNome(nome);
        clienteRequestDTO.setEmail(email);
        clienteRequestDTO.setTelefone(telefone);

        Platform.runLater(() -> {
            try {
                nomeTextField.setText("");
                emailTextField.setText("");
                telefoneTextField.setText("");
            } catch (Exception e) {
                System.err.println("Erro ao limpar os campos de texto: " + e.getMessage());
            }
        });
    }

}
