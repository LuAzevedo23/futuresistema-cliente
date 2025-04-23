package com.devlu.futuresistema_cliente.controller.controllerFX;

import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import com.devlu.futuresistema_cliente.utils.GeracaoPDF;
import com.devlu.futuresistema_cliente.utils.Impressao;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component // Só se usa um bridge Spring-JavaFX
public class RelatorioClienteFXController {

    @FXML
    private TextArea relatorioTextArea;

    @FXML
    private VBox relatorioContainer;

    @Autowired
    private ClienteService clienteService;

    private Stage stage;

    @FXML
    public void initialize() {
        gerarRelatorio(); // Gera o relatório ao inicializar a tela
    }

    @FXML
    public void gerarPDF() {
        String conteudo = relatorioTextArea.getText();
        GeracaoPDF.gerarPDF(conteudo, "relatorio_cliente");
    }

    @FXML
    public void imprimirRelatorio() {
        Node node = relatorioContainer; // Imprime o container do relatório
        Scene scene = node.getScene();

        if (scene == null) {
            System.err.println("Cena não disponível para impressão.");
            return;
        }

        Impressao.imprimir(node, scene);
    }

    private void gerarRelatorio() {
        List<ClienteDTO> clientes = clienteService.findAllClientes();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DE CLIENTES\n\n");
        for (ClienteDTO cliente : clientes) {
            relatorio.append("ID: ").append(cliente.getId()).append("\n");
            relatorio.append("Nome: ").append(cliente.getNome()).append("\n");
            relatorio.append("Email: ").append(cliente.getEmail()).append("\n");
            relatorio.append("Telefone: ").append(cliente.getTelefone()).append("\n");
            relatorio.append("------------------------\n");
        }

        relatorioTextArea.setText(relatorio.toString());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void fecharJanela() {
        if (stage != null) {
            stage.close();
        }
    }
}
