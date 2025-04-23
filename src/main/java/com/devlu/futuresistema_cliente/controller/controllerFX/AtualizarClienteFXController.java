package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.api.ClienteRequestDTO;
import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.business.ResourceNotFoundException;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtualizarClienteFXController {

    @FXML
    private TextField idTextField;

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
    public void carregarDadosCliente() {
        String idText = idTextField.getText();
        if (idText == null || idText.trim().isEmpty()) {
            Notificacao.exibirMensagem("Erro", "ID Inválido",
                    "Por favor, insira um ID válido para carregar os dados do cliente.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Long id = Long.parseLong(idText);
            ClienteDTO cliente = clienteService.findById(id);

            if (cliente != null) {
                nomeTextField.setText(cliente.getNome());
                emailTextField.setText(cliente.getEmail());
                telefoneTextField.setText(cliente.getTelefone());
            } else {
                Notificacao.exibirMensagem("Erro", "Cliente Não Encontrado",
                        "Não foi possível encontrar um cliente com o ID " + id + ".", Alert.AlertType.ERROR);
                mensagemLabel.setText("Cliente não encontrado!");
            }
        } catch (NumberFormatException e) {
            Notificacao.exibirMensagem("Erro", "ID Inválido",
                    "Por favor, insira um ID numérico válido.", Alert.AlertType.ERROR);
        } catch (ResourceNotFoundException e) {
            Notificacao.exibirMensagem("Erro", "Cliente Não Encontrado",
                    e.getMessage(), Alert.AlertType.ERROR);
            mensagemLabel.setText("Cliente não encontrado!");
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Carregar Cliente",
                    "Ocorreu um erro ao carregar os dados do cliente.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void atualizarCliente() {
        String idText = idTextField.getText();
        String nome = nomeTextField.getText();
        String email = emailTextField.getText();
        String telefone = telefoneTextField.getText();

        if (idText == null || idText.trim().isEmpty() || nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty() || telefone == null || telefone.trim().isEmpty()) {
            Notificacao.exibirMensagem("Erro", "Dados Inválidos",
                    "Por favor, preencha todos os campos para atualizar o cliente.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Long id = Long.parseLong(idText);
            ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
            clienteRequestDTO.setNome(nome);
            clienteRequestDTO.setEmail(email);
            clienteRequestDTO.setTelefone(telefone);

            ClienteDTO clienteAtualizado = clienteService.update(id, clienteRequestDTO);

            Notificacao.exibirMensagem("Sucesso", "Cliente Atualizado",
                    "O cliente " + nome + " foi atualizado com sucesso!", Alert.AlertType.INFORMATION);
            mensagemLabel.setText("Cliente atualizado com sucesso!");
        } catch (NumberFormatException e) {
            Notificacao.exibirMensagem("Erro", "ID Inválido",
                    "Por favor, insira um ID numérico válido.", Alert.AlertType.ERROR);
        } catch (ResourceNotFoundException e) {
            Notificacao.exibirMensagem("Erro", "Cliente Não Encontrado",
                    e.getMessage(), Alert.AlertType.ERROR);
            mensagemLabel.setText("Cliente não encontrado!");
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Atualizar Cliente",
                    "Ocorreu um erro ao atualizar o cliente " + nome + ".", Alert.AlertType.ERROR);
            mensagemLabel.setText("Erro ao atualizar cliente!");
        }
    }
}
