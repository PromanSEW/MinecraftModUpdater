package promansew.mcmodupdater.parser;

import javafx.concurrent.Task;
import promansew.mcmodupdater.MCData;
import promansew.mcmodupdater.model.Mod;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModParseTask extends Task<List<Mod>> {
	@Override
	protected List<Mod> call() {
		File[] files = MCData.getMods();
		if (files == null) return Collections.emptyList();
		List<Mod> mods = new ArrayList<>(files.length);
		for (int i = 0; i < files.length; i++) {
			mods.add(ModParser.parse(files[i]));
			updateProgress(i + 1, files.length);
		}
		return mods;
	}
}
