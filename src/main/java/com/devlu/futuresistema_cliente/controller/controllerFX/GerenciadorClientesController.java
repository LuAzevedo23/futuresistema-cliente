package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.controllerFX.callbacks.ClientManagementCallback;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.utils.Notificacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para a tela principal do gerenciamento de clientes.
 * Gerencia a exibição da lista de clientes, e as ações de adicionar, editar, excluir e gerar relatórios.
 */
@Component
public class GerenciadorClientesController implements Initializable, ClientManagementCallback {

    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab clientesTab;
    @FXML
    private Tab adicionarClienteTab;
    @FXML
    private Tab editarClienteTab;
    @FXML
    private TableView<ClienteDTO> clientesTable;
    @FXML
    private TableColumn<ClienteDTO, Long> colId;
    @FXML
    private TableColumn<ClienteDTO, String> colNome;
    @FXML
    private TableColumn<ClienteDTO, String> colEmail;
    @FXML
    private TableColumn<ClienteDTO, String> colTelefone;
    @FXML
    private TableColumn<ClienteDTO, String> colStatus;
    @FXML
    private TableColumn<ClienteDTO, String> colEndereco; // Coluna para exibir o endereço

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ConfigurableApplicationContext springContext;

    private ObservableList<ClienteDTO> listaClientes;

    /**
     * Inicializa o controlador após o FXML ser completamente carregado.
     * Configura as colunas da tabela e carrega a lista inicial de clientes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Define como a coluna de endereço será preenchida
        colEndereco.setCellValueFactory(cellData -> {
            ClienteDTO cliente = cellData.getValue();
            if (cliente.getEndereco() != null) {
                // Formata o endereço de forma legível
                return new javafx.beans.property.SimpleStringProperty(
                        cliente.getEndereco().getLogradouro() + ", " +
                                (cliente.getEndereco().getNumero() != null ? cliente.getEndereco().getNumero() : "s/n") +
                                " - " + cliente.getEndereco().getBairro() +
                                ", " + cliente.getEndereco().getCidade() + " - " + cliente.getEndereco().getEstado() +
                                " CEP: " + cliente.getEndereco().getCep()
                );
            } else {
                return new javafx.beans.property.SimpleStringProperty("Não informado");
            }
        });

        listaClientes = FXCollections.observableArrayList();
        clientesTable.setItems(listaClientes);
        refreshClientList(); // Carrega os clientes ao iniciar
    }

    /**
     * Atualiza a lista de clientes na tabela, buscando os dados do serviço.
     */
    @Override
    public void refreshClientList() {
        try {
            List<ClienteDTO> clientes = clienteService.findAll();
            listaClientes.setAll(clientes);
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Carregar Clientes",
                    "Ocorreu um erro ao carregar a lista de clientes: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Seleciona a aba da lista de clientes.
     */
    @Override
    public void selectClientListTab() {
        mainTabPane.getSelectionModel().select(clientesTab);
    }

    /**
     * Abre a aba para adicionar um novo cliente.
     */
    @FXML
    public void adicionarCliente() {
        try {
            // AJUSTE: Nome do arquivo FXML sem espaços
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adicionar-cliente.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            AdicionarClienteFXController controller = loader.getController();
            controller.setCallback(this); // Passa a referência para este controlador (callback)

            adicionarClienteTab.setContent(root);
            mainTabPane.getSelectionModel().select(adicionarClienteTab); // Seleciona a aba
        } catch (IOException e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Abrir Formulário de Adição",
                    "Não foi possível carregar a tela de adição de cliente. Verifique se o arquivo 'adicionar-cliente.fxml' existe e está na pasta correta. Detalhes: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            Notificacao.exibirMensagem("Erro", "Erro Inesperado",
                    "Ocorreu um erro inesperado ao tentar adicionar um cliente. Detalhes: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Abre a aba para editar um cliente selecionado.
     */
    @FXML
    public void editarCliente() {
        ClienteDTO clienteSelecionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                // AJUSTE: Nome do arquivo FXML sem espaços
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/atualizar-cliente.fxml"));
                loader.setControllerFactory(springContext::getBean);
                Parent root = loader.load();

                AtualizarClienteFXController controller = loader.getController();
                controller.setCallback(this); // Passa a referência para este controlador (callback)
                controller.setClienteParaEditar(clienteSelecionado); // Preenche os campos com os dados do cliente

                editarClienteTab.setContent(root);
                mainTabPane.getSelectionModel().select(editarClienteTab); // Seleciona a aba
            } catch (IOException e) {
                Notificacao.exibirMensagem("Erro", "Erro ao Abrir Formulário de Edição",
                        "Não foi possível carregar a tela de edição de cliente. Verifique se o arquivo 'atualizar-cliente.fxml' existe e está na pasta correta. Detalhes: " + e.getMessage(),
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            } catch (Exception e) {
                Notificacao.exibirMensagem("Erro", "Erro Inesperado",
                        "Ocorreu um erro inesperado ao tentar editar um cliente. Detalhes: " + e.getMessage(),
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Notificacao.exibirMensagem("Aviso", "Nenhum Cliente Selecionado",
                    "Por favor, selecione um cliente na tabela para editar.",
                    Alert.AlertType.WARNING);
        }
    }

    /**
     * Exclui o cliente selecionado da tabela e do banco de dados.
     */
    @FXML
    public void excluirCliente() {
        ClienteDTO clienteSelecionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Optional<ButtonType> result = Notificacao.exibirConfirmacao("Confirmar Exclusão",
                    "Tem certeza que deseja excluir o cliente '" + clienteSelecionado.getNome() + "'?",
                    "Esta ação não pode ser desfeita.");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    clienteService.delete(clienteSelecionado.getId());
                    Notificacao.exibirMensagem("Sucesso", "Cliente Excluído",
                            "O cliente '" + clienteSelecionado.getNome() + "' foi excluído com sucesso.",
                            Alert.AlertType.INFORMATION);
                    refreshClientList(); // Atualiza a tabela após a exclusão
                } catch (Exception e) {
                    Notificacao.exibirMensagem("Erro", "Erro ao Excluir Cliente",
                            "Ocorreu um erro ao excluir o cliente: " + e.getMessage(),
                            Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
        } else {
            Notificacao.exibirMensagem("Aviso", "Nenhum Cliente Selecionado",
                    "Por favor, selecione um cliente na tabela para excluir.",
                    Alert.AlertType.WARNING);
        }
    }

    /**
     * Gera e exibe um relatório detalhado do cliente selecionado.
     */
    @FXML
    public void gerarRelatorioClientes() {
        ClienteDTO clienteSelecionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                // Caminho do FXML para o relatório, já estava correto
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorio-cliente.fxml"));
                loader.setControllerFactory(springContext::getBean); // Garante que o Spring gerencie o controlador
                Parent root = loader.load();

                RelatorioClienteFXController controller = loader.getController();
                controller.setCallback(this); // Define o callback para retornar à lista principal
                controller.carregarRelatorio(clienteSelecionado.getId()); // Carrega o relatório para o cliente selecionado

                Stage reportStage = new Stage();
                reportStage.setTitle("Relatório do Cliente: " + clienteSelecionado.getNome());
                reportStage.setScene(new javafx.scene.Scene(root));
                reportStage.showAndWait(); // Abre como janela modal
            } catch (IOException e) {
                Notificacao.exibirMensagem("Erro", "Erro ao Gerar Relatório",
                        "Não foi possível carregar a tela de relatório. Detalhes: " + e.getMessage(),
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            } catch (Exception e) {
                Notificacao.exibirMensagem("Erro", "Erro Inesperado",
                        "Ocorreu um erro inesperado ao tentar gerar o relatório. Detalhes: " + e.getMessage(),
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Notificacao.exibirMensagem("Aviso", "Nenhum Cliente Selecionado",
                    "Por favor, selecione um cliente na tabela para gerar o relatório.",
                    Alert.AlertType.WARNING);
        }
    }

    /**
     * Fecha a janela principal do aplicativo.
     */
    @FXML
    public void fecharJanela() {
        Stage stage = (Stage) mainTabPane.getScene().getWindow();
        stage.close();
    }
}