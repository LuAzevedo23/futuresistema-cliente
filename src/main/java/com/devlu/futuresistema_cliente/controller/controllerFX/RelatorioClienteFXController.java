package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.controllerFX.callbacks.ClientManagementCallback;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.entities.AuditEntry;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para a tela de relatório detalhado do cliente.
 */
@Component
public class RelatorioClienteFXController implements Initializable {

    @FXML
    private Label clientNameReportLabel;
    @FXML
    private TextArea relatorioTextArea;

    @Autowired
    private ClienteService clienteService;

    private ClientManagementCallback callback;

    /**
     * ADIÇÃO: Método para injetar a referência do callback, permitindo o retorno à tela principal.
     *
     * @param callback A instância do callback a ser utilizada.
     */
    public void setCallback(ClientManagementCallback callback) {
        this.callback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada de especial para inicializar aqui, os dados são carregados via carregarRelatorio()
    }

    /**
     * ADIÇÃO: Carrega os dados do relatório para um cliente específico.
     * Este método busca os detalhes do cliente e seu histórico de auditoria.
     *
     * @param clientId O ID do cliente para o qual o relatório será gerado.
     */
    public void carregarRelatorio(Long clientId) {
        try {
            ClienteDTO cliente = clienteService.findById(clientId);
            if (cliente != null) {
                clientNameReportLabel.setText("Cliente: " + cliente.getNome() + " (ID: " + cliente.getId() + ")");

                StringBuilder reportContent = new StringBuilder();
                reportContent.append("--- DETALHES DO CLIENTE ---\n");
                reportContent.append("Nome: ").append(cliente.getNome()).append("\n");
                reportContent.append("Email: ").append(cliente.getEmail()).append("\n");
                reportContent.append("Telefone: ").append(cliente.getTelefone()).append("\n");
                reportContent.append("Status: ").append(cliente.getStatus()).append("\n");

                if (cliente.getEndereco() != null) {
                    reportContent.append("\n--- ENDEREÇO ---\n");
                    reportContent.append("CEP: ").append(cliente.getEndereco().getCep()).append("\n");
                    reportContent.append("Logradouro: ").append(cliente.getEndereco().getLogradouro()).append("\n");
                    reportContent.append("Número: ").append(cliente.getEndereco().getNumero() != null ? cliente.getEndereco().getNumero() : "N/A").append("\n");
                    reportContent.append("Complemento: ").append(cliente.getEndereco().getComplemento() != null ? cliente.getEndereco().getComplemento() : "N/A").append("\n");
                    reportContent.append("Bairro: ").append(cliente.getEndereco().getBairro()).append("\n");
                    reportContent.append("Cidade: ").append(cliente.getEndereco().getCidade()).append("\n");
                    reportContent.append("Estado: ").append(cliente.getEndereco().getEstado()).append("\n");
                    reportContent.append("Status Endereço: ").append(cliente.getEndereco().getStatus() != null ? cliente.getEndereco().getStatus() : "N/A").append("\n");
                }

                List<AuditEntry> auditoria = clienteService.findAuditEntriesForClient(clientId);
                if (!auditoria.isEmpty()) {
                    reportContent.append("\n--- HISTÓRICO DE AUDITORIA ---\n");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    for (AuditEntry entry : auditoria) {
                        reportContent.append("Data/Hora: ").append(entry.getTimestamp().format(formatter)).append("\n");
                        reportContent.append("Entidade: ").append(entry.getEntityType()).append(" (ID: ").append(entry.getEntityId()).append(")\n");
                        reportContent.append("Operação: ").append(entry.getOperationType()).append("\n");
                        reportContent.append("Detalhes: ").append(entry.getDetails()).append("\n");
                        reportContent.append("---\n");
                    }
                } else {
                    reportContent.append("\n--- HISTÓRICO DE AUDITORIA ---\n");
                    reportContent.append("Nenhuma entrada de auditoria encontrada para este cliente.\n");
                }

                relatorioTextArea.setText(reportContent.toString());

            } else {
                clientNameReportLabel.setText("Cliente Não Encontrado");
                relatorioTextArea.setText("Não foi possível carregar os detalhes do relatório para o cliente com ID: " + clientId);
            }
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Carregar Relatório",
                    "Ocorreu um erro ao carregar os dados do relatório: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            clientNameReportLabel.setText("Erro ao Carregar Relatório");
            relatorioTextArea.setText("Detalhes do erro: " + e.getMessage());
        }
    }

    /**
     * Fecha a janela de relatório e retorna à lista de clientes.
     *
     * @param event Evento de ação.
     */
    @FXML
    public void voltarParaListaClientes(ActionEvent event) {
        Stage stage = (Stage) relatorioTextArea.getScene().getWindow();
        stage.close();
        if (callback != null) {
            callback.selectClientListTab(); // Retorna para a aba principal de clientes
        }
    }
}