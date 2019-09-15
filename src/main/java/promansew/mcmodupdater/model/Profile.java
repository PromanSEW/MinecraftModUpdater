package promansew.mcmodupdater.model;

import java.time.LocalDateTime;

public class Profile implements Comparable<Profile> {
	public final String version;
	public final LocalDateTime lastPlayed;

	public Profile(String version, LocalDateTime lastPlayed) {
		this.version = version;
		this.lastPlayed = lastPlayed;
	}

	@Override
	public int compareTo(Profile other) {
		return lastPlayed.compareTo(other.lastPlayed);
	}

	@Override
	public String toString() {
		return String.format("[version: %s, lastPlayed: %s]", version, lastPlayed);
	}
}
