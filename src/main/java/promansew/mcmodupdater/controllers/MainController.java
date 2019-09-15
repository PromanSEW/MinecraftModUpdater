package promansew.mcmodupdater.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import promansew.mcmodupdater.MCData;
import promansew.mcmodupdater.Main;
import promansew.mcmodupdater.model.Mod;
import promansew.mcmodupdater.parser.ModParseTask;

import java.text.MessageFormat;
import java.util.List;

public class MainController {

	@FXML
	private Label version;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private TableView<Mod> mods;

	@FXML
	public void initialize() {
		version.setText(MessageFormat.format(Main.STRINGS.getString("version.mc"), MCData.getVersion()));
		initTable();
		loadMods();
	}

	private void initTable() {
		ObservableList<TableColumn<Mod, ?>> columns = mods.getColumns();
		TableColumn<?, ?> names = columns.get(0);
		TableColumn<?, ?> versions = columns.get(1);
		TableColumn<?, ?> updates = columns.get(2);
		names.setCellValueFactory(new PropertyValueFactory<>("name"));
		versions.setCellValueFactory(new PropertyValueFactory<>("version"));
		names.prefWidthProperty().bind(mods.widthProperty().multiply(0.5));
		versions.prefWidthProperty().bind(mods.widthProperty().multiply(0.25));
		updates.prefWidthProperty().bind(mods.widthProperty().multiply(0.25));
		names.setResizable(false);
		versions.setResizable(false);
		updates.setResizable(false);
	}

	/** Загрузить список модов */
	private void loadMods() {
		Task<List<Mod>> task = new ModParseTask();
		progressBar.progressProperty().bind(task.progressProperty());
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event -> {
			progressBar.setVisible(false);
			mods.setItems(FXCollections.observableList(task.getValue()));
		});
		Platform.runLater(() -> new Thread(task).start());
	}
}
