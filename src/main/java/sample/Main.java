package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static final double MINUTES_TO_PIXEL = 1.2;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Main.class.getResource("/sample.fxml"));
		root.lookup("stackPane");
		primaryStage.setTitle("Schedule Checker");
		Scene scene = new Scene(root);
		scene.setUserAgentStylesheet("basic.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
