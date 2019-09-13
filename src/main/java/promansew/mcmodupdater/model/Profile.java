package promansew.mcmodupdater.model;

import java.time.LocalDateTime;

public class Profile {
	public final String version;
	public final LocalDateTime lastPlayed;

	public Profile(String version, LocalDateTime lastPlayed) {
		this.version = version;
		this.lastPlayed = lastPlayed;
	}
}
