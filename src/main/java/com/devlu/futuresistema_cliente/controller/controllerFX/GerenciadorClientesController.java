package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.controller.dto.EnderecoDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GerenciadorClientesController {

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
    private TableColumn<ClienteDTO, String> colEndereco;

    private final ObservableList<ClienteDTO> clientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // Popula a coluna de endereço com uma string formatada
        colEndereco.setCellValueFactory(cellData ->
                new SimpleStringProperty(formatarEndereco(cellData.getValue().getEndereco()))
        );

        // Dados exemplo (ajuste para buscar do banco depois)
        clientes.add(new ClienteDTO(
                1L, "Maria Souza", "maria@mail.com", "11999999999",
                new EnderecoDTO("Rua Exemplo, 123", "Centro", "São Paulo", "SP", "12345-000")
        ));
        clientes.add(new ClienteDTO(
                2L, "João Lima", "joao@mail.com", "21988888888",
                new EnderecoDTO("Av. Brasil, 456", "Bairro Novo", "Rio de Janeiro", "RJ", "22222-111")
        ));

        clientesTable.setItems(clientes);
    }

    private String formatarEndereco(EnderecoDTO e) {
        if (e == null) return "";
        return String.format("%s, %s, %s-%s, %s",
                nvl(e.getLogradouro()), nvl(e.getBairro()), nvl(e.getCidade()), nvl(e.getEstado()), nvl(e.getCep())
        );
    }

    private String nvl(String v) {
        return v != null ? v : "";
    }

    @FXML
    private void adicionarCliente() {
        // Exemplo de adição rápida para teste
        long novoId = clientes.size() + 1;
        clientes.add(new ClienteDTO(
                novoId, "Novo Cliente " + novoId, "novo" + novoId + "@mail.com", "000000000",
                new EnderecoDTO("Rua Nova, " + novoId, "Bairro", "Cidade", "UF", "00000-000")
        ));
        System.out.println("Cliente adicionado!");
    }

    @FXML
    private void editarCliente() {
        ClienteDTO selecionado = clientesTable.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            System.out.println("Editar cliente: " + selecionado.getNome());
            // Implementar edição (abrir form, etc.)
        } else {
            System.out.println("Selecione um cliente para editar.");
        }
    }

    @FXML
    private void excluirCliente() {
        ClienteDTO selecionado = clientesTable.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            clientes.remove(selecionado);
            System.out.println("Cliente excluído!");
        } else {
            System.out.println("Selecione um cliente para excluir.");
        }
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) clientesTable.getScene().getWindow();
        stage.close();
    }
}
