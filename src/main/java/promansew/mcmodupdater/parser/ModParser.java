package promansew.mcmodupdater.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import promansew.mcmodupdater.model.Mod;

import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModParser {

	private static final Pattern PATTERN = Pattern.compile("[0-9\\[\\].]*([a-zA-Z_-]+)[_-]([v]*[0-9-.]+[\\w.]*)\\.jar");

	public static Mod parse(File file) {
		String filename = file.getName();
		JSONObject info = getModInfo(file);
		if (info == null) return new Mod(getName(filename), getVersion(filename));
		String name = info.optString("name", null);
		if (name == null) {
			JSONArray modList = info.optJSONArray("modList");
			if (modList == null) {
				name = getName(filename);
			} else {
				info = modList.optJSONObject(0);
				name = info == null ? getName(filename) : info.optString("name");
				if (name == null) name = getName(filename);
			}
		}
		String version = info == null ? null : info.optString("version", null);
		if (version == null || version.isEmpty()) version = getVersion(filename);
		return new Mod(name, version);
	}

	/** @return JSON из {@code mcmod.info} */
	private static JSONObject getModInfo(File file) {
		try {
			FileSystem fs = FileSystems.newFileSystem(file.toPath(), null);
			try (InputStream in = Files.newInputStream(fs.getPath("/mcmod.info"))) {
				Object json = new JSONTokener(in).nextValue();
				if (json instanceof JSONObject) {
					return (JSONObject) json;
				} else if (json instanceof JSONArray) {
					return ((JSONArray) json).getJSONObject(0);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static String getName(String file) {
		Matcher matcher = PATTERN.matcher(file);
		return matcher.matches() ? matcher.group(1) : file;
	}

	/** */
	private static String getVersion(String file) {
		Matcher matcher = PATTERN.matcher(file);
		return matcher.matches() ? matcher.group(2) : "unknown";
	}
}
