package com.devlu.futuresistema_cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FuturesistemaClienteApplication {

	public static void main(String[] args) {

		SpringApplication.run(FuturesistemaClienteApplication.class, args);
	}



	/*
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}

	static class JavaFxApplication extends Application {
		@Override
		public void init() throws Exception {
			context = new SpringApplicationBuilder(FuturesistemaClienteApplication.class).run();
		}

		@Override
		public void start(javafx.stage.Stage primaryStage) throws Exception {
			// Aqui você pode carregar a sua tela principal do JavaFX
			// e exibi-la no primaryStage
		}


		@Override
		public void stop() throws Exception {
			context.close();
			Platform.exit();
		}
		*/
	}


