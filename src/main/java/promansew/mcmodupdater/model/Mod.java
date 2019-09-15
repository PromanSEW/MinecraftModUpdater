package promansew.mcmodupdater.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mod {
	private final StringProperty name;
	private final StringProperty version;
	public final String updateUrl;

	public Mod(String name, String version, String updateUrl) {
		this.name = new SimpleStringProperty(this, "name");
		this.name.setValue(name);
		this.version = new SimpleStringProperty(this, "version");
		this.version.setValue(version);
		this.updateUrl = updateUrl;
	}

	public String getName() {
		return name.get();
	}

	public String getVersion() {
		return version.get();
	}

	@SuppressWarnings("unused")
	public StringProperty nameProperty() {
		return name;
	}

	@SuppressWarnings("unused")
	public StringProperty versionProperty() {
		return version;
	}

	@Override
	public String toString() {
		return String.format("[name: %s, version: %s]", name.get(), version.get());
	}
}
