package promansew.mcmodupdater.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import promansew.mcmodupdater.MCData;
import promansew.mcmodupdater.model.Mod;

public class MainController {

	@FXML
	private TableView<Mod> mods;

	@FXML
	@SuppressWarnings("unchecked")
	public void initialize() {
		ObservableList<TableColumn<Mod, ?>> columns = mods.getColumns();
		TableColumn<Mod, ?> names = columns.get(0);
		TableColumn<Mod, ?> versions = columns.get(1);
		names.setCellValueFactory(new PropertyValueFactory("name"));
		versions.setCellValueFactory(new PropertyValueFactory("version"));
		names.prefWidthProperty().bind(mods.widthProperty().multiply(0.6));
		versions.prefWidthProperty().bind(mods.widthProperty().multiply(0.4));
		names.setResizable(false);
		versions.setResizable(false);
		mods.setItems(FXCollections.observableList(MCData.getMods()));
	}
}
