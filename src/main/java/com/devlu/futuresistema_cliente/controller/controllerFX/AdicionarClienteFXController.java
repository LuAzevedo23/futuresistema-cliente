package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.api.ClienteRequestDTO;
import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdicionarClienteFXController {

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

    @FXML
    public void adicionarCliente() {
        String nome = nomeTextField.getText();
        String email = emailTextField.getText();
        String telefone = telefoneTextField.getText();

        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty() || telefone == null || telefone.trim().isEmpty()) {
            Notificacao.exibirMensagem("Erro", "Dados Inválidos",
                    "Por favor, preencha todos os campos para adicionar o cliente.", Alert.AlertType.ERROR);
            return;
        }

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNome(nome);
        clienteRequestDTO.setEmail(email);
        clienteRequestDTO.setTelefone(telefone);

        try {
            ClienteDTO clienteAdicionado = clienteService.insert(clienteRequestDTO);
            Notificacao.exibirMensagem("Sucesso", "Cliente Adicionado",
                    "O cliente " + nome + " foi adicionado com sucesso!", Alert.AlertType.INFORMATION);
            mensagemLabel.setText("Cliente adicionado com sucesso!");
            nomeTextField.clear();
            emailTextField.clear();
            telefoneTextField.clear();
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Adicionar Cliente",
                    "Ocorreu um erro ao adicionar o cliente " + nome + ".", Alert.AlertType.ERROR);
            mensagemLabel.setText("Erro ao adicionar cliente!");
        }
    }
}
