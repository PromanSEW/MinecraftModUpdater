package promansew.mcmodupdater;

import org.json.JSONObject;
import org.json.JSONTokener;
import promansew.mcmodupdater.model.Mod;
import promansew.mcmodupdater.model.Profile;
import promansew.mcmodupdater.parser.ModParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

	/** @return Список модов */
	public static List<Mod> getMods() {
		File path = new File(PATH, "mods");
		if (!path.exists()) return Collections.emptyList();
		File[] files = path.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
		if (files == null) return Collections.emptyList();
		List<Mod> mods = new ArrayList<>(files.length);
		for (File file : files) {
			Mod mod = ModParser.parse(file);
			System.out.println(mod);
			mods.add(mod);
		}
		return mods;
	}
}
