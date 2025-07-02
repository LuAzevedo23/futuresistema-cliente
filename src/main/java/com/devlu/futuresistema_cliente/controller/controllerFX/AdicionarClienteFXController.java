package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.controllerFX.callbacks.ClientManagementCallback;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para a tela de adição de cliente.
 * Gerencia o formulário para adicionar novos clientes.
 */
@Component
public class AdicionarClienteFXController implements Initializable {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telefoneField;
    @FXML
    private ComboBox<String> statusComboBox; // ComboBox para o status
    @FXML
    private TextField cepField;
    @FXML
    private TextField logradouroField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField complementoField;
    @FXML
    private TextField bairroField;
    @FXML
    private TextField cidadeField;
    @FXML
    private TextField estadoField;

    @Autowired
    private ClienteService clienteService;

    private ClientManagementCallback callback;

    /**
     * Define o callback para permitir que este controlador se comunique com o controlador pai
     * (GerenciadorClientesController), para, por exemplo, atualizar a lista após uma ação.
     * @param callback A instância do callback.
     */
    public void setCallback(ClientManagementCallback callback) {
        this.callback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa o ComboBox de status
        statusComboBox.getItems().addAll("ATIVO", "INATIVO", "EXCLUIDO");
        statusComboBox.getSelectionModel().select("ATIVO"); // Define um valor padrão
    }

    /**
     * Salva um novo cliente no banco de dados.
     * @param event Evento de ação.
     */
    @FXML
    public void salvarCliente(ActionEvent event) {
        try {
            // Cria um objeto EnderecoDTO com os dados do formulário
            EnderecoDTO endereco = new EnderecoDTO();
            endereco.setCep(cepField.getText());
            endereco.setLogradouro(logradouroField.getText());
            endereco.setNumero(numeroField.getText().isEmpty() ? null :
                    Long.valueOf(numeroField.getText()));
            endereco.setComplemento(complementoField.getText());
            endereco.setBairro(bairroField.getText());
            endereco.setCidade(cidadeField.getText());
            endereco.setEstado(estadoField.getText());

            // Cria um objeto ClienteDTO com os dados do formulário
            ClienteDTO novoCliente = new ClienteDTO();
            novoCliente.setNome(nomeField.getText());
            novoCliente.setEmail(emailField.getText());
            novoCliente.setTelefone(telefoneField.getText());
            novoCliente.setStatus(statusComboBox.getValue());
            novoCliente.setEndereco(endereco); // Associa o endereço ao cliente

            // Chama o serviço para salvar o cliente
            clienteService.save(novoCliente);

            Notificacao.exibirMensagem("Sucesso", "Cliente Adicionado", "Cliente " + novoCliente.getNome() + " adicionado com sucesso!", Alert.AlertType.INFORMATION);

            // Fecha a janela ou aba atual e volta para a lista principal
            fecharJanelaOuAba(event);
            if (callback != null) {
                callback.refreshClientList(); // Atualiza a lista no controlador pai
                callback.selectClientListTab(); // Volta para a aba da lista
            }

        } catch (NumberFormatException e) {
            Notificacao.exibirMensagem("Erro de Entrada", "Erro no Número", "O campo 'Número' deve conter apenas números válidos.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            Notificacao.exibirMensagem("Erro de Validação", "Dados Inválidos", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Salvar Cliente", "Ocorreu um erro ao salvar o cliente: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Cancela a operação e fecha a tela atual.
     * @param event Evento de ação.
     */
    @FXML
    public void cancelar(ActionEvent event) {
        fecharJanelaOuAba(event);
        if (callback != null) {
            callback.selectClientListTab(); // Volta para a aba da lista
        }
    }

    /**
     * Método auxiliar para fechar a janela ou aba atual.
     * @param event Evento de ação.
     */
    private void fecharJanelaOuAba(ActionEvent event) {
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }
}