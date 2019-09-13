package promansew.mcmodupdater.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mod {
	private final StringProperty name;
	private final StringProperty version;

	public Mod(String name, String version) {
		this.name = new SimpleStringProperty(this, "name");
		this.name.setValue(name);
		this.version = new SimpleStringProperty(this, "version");
		this.version.setValue(version);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty versionProperty() {
		return version;
	}

	@Override
	public String toString() {
		return String.format("[name: %s, version: %s]", name.getValue(), version.getValue());
	}
}
