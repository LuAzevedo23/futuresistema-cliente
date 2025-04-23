package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.business.ResourceNotFoundException;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ExcluirClienteFXController {

    @FXML
    private TextField idTextField;

    @FXML
    private Label mensagemLabel;

    @Autowired
    private ClienteService clienteService;

    @FXML
    public void excluirCliente() {
        String idText = idTextField.getText();
        if (idText == null || idText.trim().isEmpty()) {
            Notificacao.exibirMensagem("Erro", "ID Inválido",
                    "Por favor, insira um ID válido para excluir o cliente.",
                    Alert.AlertType.ERROR);
            return;
        }

        try {
            Long id = Long.parseLong(idText);
            clienteService.delete(id);
            Notificacao.exibirMensagem("Sucesso", "Cliente Excluído",
                    "O cliente com ID " + id + " foi excluído com sucesso!",
                    Alert.AlertType.INFORMATION);
            mensagemLabel.setText("Cliente excluído com sucesso!");
        } catch (NumberFormatException e) {
            Notificacao.exibirMensagem("Erro", "ID Inválido",
                    "Por favor, insira um ID numérico válido.", Alert.AlertType.ERROR);
        } catch (ResourceNotFoundException e) {
            Notificacao.exibirMensagem("Erro", "Cliente Não Encontrado",
                    e.getMessage(), Alert.AlertType.ERROR);
            mensagemLabel.setText("Cliente não encontrado!");
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Excluir Cliente",
                    "Ocorreu um erro ao excluir o cliente.", Alert.AlertType.ERROR);
        }
    }
}
