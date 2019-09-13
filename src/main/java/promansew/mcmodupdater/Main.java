package promansew.mcmodupdater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ResourceBundle;

import static promansew.mcmodupdater.util.Utils.getString;

public class Main extends Application {

	private final ResourceBundle STRINGS = ResourceBundle.getBundle("Main");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		if (!MCData.PATH.exists()) {
			minecraftNotFound();
			return;
		}
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"), STRINGS);
		stage.setTitle("Minecraft Mod Updater");
		stage.setScene(new Scene(root, 854, 480));
		stage.show();
		System.out.println(MCData.getProfiles());
	}

	/** Minecraft не найден */
	private void minecraftNotFound() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(getString(STRINGS, "error"));
		alert.setHeaderText(null);
		alert.setContentText(getString(STRINGS, "error.mcnotfound"));
		if (!alert.showAndWait().isPresent()) System.exit(0);
	}
}
