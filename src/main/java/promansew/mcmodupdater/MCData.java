package promansew.mcmodupdater;

import org.json.JSONObject;
import org.json.JSONTokener;
import promansew.mcmodupdater.model.Profile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
		JSONObject json;
		try (InputStream in = new FileInputStream(new File(PATH, "launcher_profiles.json"))) {
			json = new JSONObject(new JSONTokener(in)).getJSONObject("profiles");
		} catch (IOException e) {
			return Collections.emptyList();
		}
		List<Profile> list = new ArrayList<>();
		for (String key : json.keySet()) {
			JSONObject profile = json.getJSONObject(key);
			String version = profile.getString("lastVersionId");
			if (version.startsWith("latest")) continue;
			String lastUsed = profile.getString("lastUsed");
			LocalDateTime lastPlayed = LocalDateTime.parse(lastUsed, DateTimeFormatter.ISO_DATE_TIME);
			list.add(new Profile(version, lastPlayed));
		}
		return list;
	}

	/** @return Список файлов модов, {@code null} при ошибке */
	public static File[] getMods() {
		File path = new File(PATH, "mods");
		return !path.exists() ? null : path.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
	}

	/** @return Версия Minecraft */
	public static String getVersion() {
		List<Profile> profiles = getProfiles();
		Collections.sort(profiles);
		String version = profiles.get(profiles.size() - 1).version;
		int index = version.indexOf('-');
		return index == -1 ? version : version.substring(0, index);
	}
}
