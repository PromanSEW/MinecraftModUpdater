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
	protected List<Mod> call() throws InterruptedException {
		File[] files = MCData.getMods();
		if (files == null) return Collections.emptyList();
		int i = 0;
		List<Mod> mods = new ArrayList<>(files.length);
		for (File file : files) {
			Mod mod = ModParser.parse(file);
			System.out.println(mod);
			mods.add(mod);
			i++;
			updateProgress(i, files.length);
		}
		return mods;
	}
}
