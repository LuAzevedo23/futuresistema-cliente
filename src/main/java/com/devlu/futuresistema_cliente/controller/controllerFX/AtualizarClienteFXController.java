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
 * Controlador para a tela de atualização (edição) de cliente.
 * Gerencia o formulário para editar clientes existentes.
 */
@Component
public class AtualizarClienteFXController implements Initializable {

    @FXML
    private TextField idField; // Campo para exibir o ID (não editável)
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
    private ClienteDTO clienteAtual; // Cliente que está sendo editado

    /**
     * Define o callback para permitir que este controlador se comunique com o controlador pai.
     * @param callback A instância do callback.
     */
    public void setCallback(ClientManagementCallback callback) {
        this.callback = callback;
    }

    /**
     * Define o cliente a ser editado e preenche os campos do formulário.
     * @param cliente O ClienteDTO a ser carregado no formulário.
     */
    public void setClienteParaEditar(ClienteDTO cliente) {
        this.clienteAtual = cliente;
        if (cliente != null) {
            idField.setText(String.valueOf(cliente.getId()));
            nomeField.setText(cliente.getNome());
            emailField.setText(cliente.getEmail());
            telefoneField.setText(cliente.getTelefone());
            statusComboBox.getSelectionModel().select(cliente.getStatus());

            if (cliente.getEndereco() != null) {
                EnderecoDTO endereco = cliente.getEndereco();
                cepField.setText(endereco.getCep());
                logradouroField.setText(endereco.getLogradouro());
                // Garante que numeroField exiba um String vazia se o número for nulo
                numeroField.setText(endereco.getNumero() != null ? String.valueOf(endereco.getNumero()) : "");
                complementoField.setText(endereco.getComplemento());
                bairroField.setText(endereco.getBairro());
                cidadeField.setText(endereco.getCidade());
                estadoField.setText(endereco.getEstado());
            } else {
                // Limpa os campos de endereço se não houver endereço associado
                cepField.setText("");
                logradouroField.setText("");
                numeroField.setText("");
                complementoField.setText("");
                bairroField.setText("");
                cidadeField.setText("");
                estadoField.setText("");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa o ComboBox de status
        statusComboBox.getItems().addAll("ATIVO", "INATIVO", "EXCLUIDO");
        // O campo ID geralmente não é editável, então podemos desabilitá-lo.
        idField.setEditable(false);
    }

    /**
     * Atualiza o cliente no banco de dados com os dados do formulário.
     * @param event Evento de ação.
     */
    @FXML
    public void atualizarCliente(ActionEvent event) {
        if (clienteAtual == null) {
            Notificacao.exibirMensagem("Erro", "Nenhum Cliente Selecionado", "Não há cliente para atualizar.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Atualiza o objeto ClienteDTO com os novos dados do formulário
            clienteAtual.setNome(nomeField.getText());
            clienteAtual.setEmail(emailField.getText());
            clienteAtual.setTelefone(telefoneField.getText());
            clienteAtual.setStatus(statusComboBox.getValue());

            // Atualiza ou cria o EnderecoDTO
            EnderecoDTO endereco = clienteAtual.getEndereco();
            if (endereco == null) {
                endereco = new EnderecoDTO();
                clienteAtual.setEndereco(endereco);
            }
            endereco.setCep(cepField.getText());
            endereco.setLogradouro(logradouroField.getText());
            // Converte o texto do número para Long, tratando campo vazio como null
            endereco.setNumero(numeroField.getText().isEmpty() ? null : Long.valueOf(numeroField.getText()));
            endereco.setComplemento(complementoField.getText());
            endereco.setBairro(bairroField.getText());
            endereco.setCidade(cidadeField.getText());
            endereco.setEstado(estadoField.getText());

            // Chama o serviço para atualizar o cliente
            // Esta chamada agora deve funcionar com o método update(ClienteDTO) no ClienteService
            clienteService.update(clienteAtual);

            Notificacao.exibirMensagem("Sucesso", "Cliente Atualizado", "Cliente " + clienteAtual.getNome() + " atualizado com sucesso!", Alert.AlertType.INFORMATION);

            // Fecha a janela ou aba atual e volta para a lista principal
            fecharJanelaOuAba(event);
            if (callback != null) {
                callback.refreshClientList(); // Atualiza a lista no controlador pai
                callback.selectClientListTab(); // Volta para a aba da lista
            }

        } catch (NumberFormatException e) {
            Notificacao.exibirMensagem("Erro de Entrada", "Erro no Número", "O campo 'Número' deve conter apenas números válidos. Detalhes: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            // Captura validações lançadas pelo ClienteService
            Notificacao.exibirMensagem("Erro de Validação", "Dados Inválidos", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Atualizar Cliente", "Ocorreu um erro inesperado ao atualizar o cliente. Detalhes: " + e.getMessage(), Alert.AlertType.ERROR);
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
        // Usa qualquer campo para obter a cena e o estágio
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }
}