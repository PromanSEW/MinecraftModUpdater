package promansew.mcmodupdater;

import org.json.JSONObject;
import org.json.JSONTokener;
import promansew.mcmodupdater.model.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class MCData {

	public static final File PATH = getPath();

	/** @return Папка Minecraft */
	private static File getPath() {
		String userHomeDir = System.getProperty("user.home", ".");
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		String mcDir = ".minecraft";
		String appDataDir;
		if (os.contains("win") && (appDataDir = System.getenv("APPDATA")) != null) {
			return new File(appDataDir, mcDir);
		} else if (os.contains("mac")) {
			appDataDir = "Application Support";
			return new File(new File(new File(userHomeDir, "Library"), appDataDir), "Minecraft");
		}
		return new File(userHomeDir, mcDir);
	}

	/** @return Список профилей */
	public static List<Profile> getProfiles() {
		InputStream in;
		try {
			in = new FileInputStream(new File(PATH, "launcher_profiles.json"));
		} catch (FileNotFoundException e) {
			return Collections.emptyList();
		}
		JSONObject json = new JSONObject(new JSONTokener(in)).getJSONObject("profiles");
		List<Profile> list = new ArrayList<>();
		for (String key : json.keySet()) {
			JSONObject profile = json.getJSONObject(key);
			LocalDateTime lastPlayed = LocalDateTime.parse(profile.getString("lastUsed"), DateTimeFormatter.ISO_DATE_TIME);
			list.add(new Profile(profile.getString("lastVersionId"), lastPlayed));
		}
		return list;
	}
}
