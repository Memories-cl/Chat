package AppClient;

import java.io.IOException;

import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Login ma = new Login();
		ma.start(new Stage());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
