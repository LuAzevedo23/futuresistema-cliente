package com.devlu.futuresistema_cliente;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent; // IMPORTANTE: Adicionado para tratamento de eventos da janela
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Classe principal para lançar a aplicação JavaFX integrada com Spring Boot.
 * Gerencia o ciclo de vida da aplicação Spring e da interface gráfica JavaFX.
 */
public class AppLauncher extends Application {

    private ConfigurableApplicationContext springContext;
    // Não precisamos de uma referência ao primaryStage aqui, pois ele é passado para showMainView.

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

        // *** NOVO AJUSTE CRUCIAL: IMPEDE O FECHAMENTO IMPLÍCITO DA JVM PELO JAVAFX ***
        // Isso é fundamental para que o contexto Spring Boot (e o H2 Console) continue rodando
        // mesmo que todas as janelas JavaFX sejam fechadas ou escondidas.
        Platform.setImplicitExit(false);
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

            // *** NOVO AJUSTE CRUCIAL: TRATAMENTO DO EVENTO DE FECHAMENTO DA JANELA (Botão 'X') ***
            // Em vez de fechar toda a aplicação, apenas esconde a janela principal.
            // Isso mantém o contexto do Spring Boot (e o H2 Console) ativo em segundo plano.
            primaryStage.setOnCloseRequest(event -> {
                event.consume(); // Impede o fechamento padrão da janela (que tentaria fechar a JVM)
                primaryStage.hide(); // Apenas esconde a janela, mantendo o Spring Boot ativo
                // Para encerrar a aplicação completamente, um botão "Sair" na UI seria necessário,
                // chamando Platform.exit() e springContext.close().
            });

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
     * Método chamado quando a aplicação JavaFX é encerrada (via Platform.exit()).
     * Garante o fechamento do contexto Spring Boot.
     * Este método só é invocado se `Platform.exit()` for explicitamente chamado (por exemplo, por um botão "Sair").
     */
    @Override
    public void stop() {
        // Verifica se o contexto Spring está ativo antes de tentar fechá-lo
        if (springContext != null && springContext.isActive()) {
            this.springContext.close(); // Fecha o contexto do Spring Boot
            System.out.println("Contexto Spring Boot encerrado.");
        }
        // Não é necessário chamar Platform.exit() aqui, pois este método já é
        // parte do processo de encerramento da plataforma JavaFX.
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