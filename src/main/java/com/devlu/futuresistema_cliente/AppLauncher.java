package com.devlu.futuresistema_cliente;

import javafx.application.Application;
import javafx.application.Platform; // Importação essencial para Platform.runLater()
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Classe principal para lançar a aplicação JavaFX integrada com Spring Boot.
 * Gerencia o ciclo de vida da aplicação Spring e da interface gráfica JavaFX.
 */
public class AppLauncher extends Application {

    private ConfigurableApplicationContext springContext;

    /**
     * Método de inicialização da aplicação JavaFX.
     * Aqui, o contexto do Spring Boot é inicializado antes da interface gráfica.
     * Os argumentos passados na linha de comando são repassados ao Spring.
     */
    @Override
    public void init() {
        // Captura os argumentos da linha de comando
        String[] args = getParameters().getRaw().toArray(new String[0]);
        // Inicializa o contexto Spring Boot, executando a classe principal da sua aplicação Spring.
        this.springContext = new SpringApplicationBuilder(FuturesistemaClienteApplication.class)
                .run(args);
    }

    /**
     * Método principal para iniciar a interface gráfica JavaFX.
     * Este método é chamado após o init() e é o ponto de entrada para a UI.
     *
     * @param primaryStage O Stage (janela principal) fornecido pelo JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        showMainView(primaryStage);
    }

    /**
     * Carrega a tela principal do Gerenciador de Clientes e a exibe.
     *
     * @param primaryStage O Stage (janela) onde a interface será exibida.
     */
    private void showMainView(Stage primaryStage) {
        try {
            // Carrega o arquivo FXML da tela principal do gerenciador de clientes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GerenciadorClientes.fxml"));
            // Define o Spring como fábrica de controladores, permitindo a injeção de dependências nos controladores JavaFX
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load(); // Carrega a hierarquia de objetos da interface

            Scene scene = new Scene(root); // Cria uma nova cena com o conteúdo carregado
            primaryStage.setScene(scene); // Define a cena na janela principal
            primaryStage.setTitle("Gerenciador de Clientes"); // Define o título da janela
            primaryStage.show(); // Exibe a janela

            // AJUSTE CRUCIAL: Usando Platform.runLater para garantir que a janela venha para a frente e receba o foco
            // O Platform.runLater garante que esta operação de UI seja executada de forma segura
            // na thread de aplicação JavaFX, quando a janela já estiver renderizada.
            Platform.runLater(() -> {
                primaryStage.toFront(); // Traz a janela para a frente de todas as outras
                primaryStage.requestFocus(); // Solicita que a janela receba o foco
            });

        } catch (IOException e) {
            // Tratamento de erro caso o FXML principal não possa ser carregado
            System.err.println("Erro ao carregar o FXML principal: " + e.getMessage());
            e.printStackTrace();
            // Notificação.exibirMensagem("Erro", "Erro de Carregamento", "Não foi possível carregar a tela principal. Detalhes: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Tratamento de outros erros inesperados durante a inicialização da aplicação
            System.err.println("Erro inesperado ao iniciar a aplicação: " + e.getMessage());
            e.printStackTrace();
            // Notificação.exibirMensagem("Erro", "Erro Inesperado", "Ocorreu um erro inesperado ao iniciar a aplicação. Detalhes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método chamado quando a aplicação JavaFX é encerrada.
     * Garante o fechamento do contexto Spring Boot e a saída da plataforma JavaFX.
     */
    @Override
    public void stop() {
        this.springContext.close(); // Fecha o contexto do Spring Boot
        Platform.exit(); // Encerra a plataforma JavaFX
    }

    /**
     * Ponto de entrada principal da aplicação.
     * Lança a aplicação JavaFX.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args); // Método estático da classe Application para iniciar a aplicação JavaFX
    }
}