package settings;

import head.LogType;
import head.Logs;

import java.io.File;

public class Dirs {
	private File main = null;
	private File conf = null;
	private File moveImages = null;

	public Dirs() {

	}

	private void createIfNotExist(File f) {
		if(!f.exists())
			Logs.add(LogType.INFO, f.mkdirs() + " created dirs with path:\n" + f.getAbsolutePath(), null);
	}

	public Dirs(String main) {
		this.main = new File(main);
		createIfNotExist(this.main);

		conf = new File(main + File.separator + "conf");
		createIfNotExist(conf);
		moveImages = new File(main + File.separator + "moveImages");
		createIfNotExist(moveImages);
	}

	public File getMain() {
		return main;
	}

	public File getConf() {
		return conf;
	}

	public File getMoveImages() {
		return moveImages;
	}
}
