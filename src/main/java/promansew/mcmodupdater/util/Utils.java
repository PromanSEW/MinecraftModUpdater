package promansew.mcmodupdater.util;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public final class Utils {

	/** @return Строка из ресурсов */
	public static String getString(ResourceBundle bundle, String key) {
		return new String(bundle.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}
}
