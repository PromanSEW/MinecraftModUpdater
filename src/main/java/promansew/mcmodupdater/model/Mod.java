package promansew.mcmodupdater.model;

public class Mod {
	public final String name;
	public final String version;

	public Mod(String name, String version) {
		this.name = name;
		this.version = version;
	}

	@Override
	public String toString() {
		return String.format("[name: %s, version: %s]", name, version);
	}
}
