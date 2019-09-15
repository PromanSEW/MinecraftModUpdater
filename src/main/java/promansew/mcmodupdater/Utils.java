package promansew.mcmodupdater;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class Utils {

	/** @return {@link ResourceBundle} в UTF-8 */
	public static ResourceBundle getResourceBundle(String name) {
		try (
				InputStream in = Utils.class.getClassLoader().getResourceAsStream(name + ".properties");
				Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
			return new PropertyResourceBundle(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
