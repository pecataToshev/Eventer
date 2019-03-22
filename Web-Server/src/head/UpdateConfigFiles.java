package head;

import content.Basics;
import settings.Config;
import settings.ConfigWebApp;
import settings.DBCredentials;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UpdateConfigFiles {
	private static Map<String, Long> lastModified = new HashMap<>();
	private static Config config = new Config();

	public static final Map<String, Class> confFileToClass;
	static {
		Map<String, Class> aMap = new HashMap<>();
		aMap.put("db.conf", DBCredentials.class);
		aMap.put("web.conf", ConfigWebApp.class);
		confFileToClass = Collections.unmodifiableMap(aMap);
	}

	public static void updateFile(String filename) {
		Class c = confFileToClass.get(filename);
		if(c == null)
			return;

		final String confDir = Config.getDirs().getConf().getAbsolutePath();
		File file = new File(confDir + File.separator + filename);
		Long lastModifiedInFile = file.lastModified();
		Long lastModifiedInSyst = lastModified.get(filename);

		// check last modified time in system
		if(lastModifiedInSyst == null) {
			lastModified.put(filename, lastModifiedInFile);
			lastModifiedInSyst = (long) -1;
		}

		// update only if the new change time is different
		if(!lastModifiedInSyst.equals(lastModifiedInFile)) {
			lastModified.replace(filename, lastModifiedInFile);

			// invoke update function
			final String className = c.getSimpleName();
			try {
				Method setFunction = config.getClass().getDeclaredMethod("set" + className, c);
				setFunction.invoke(config, Config.MAPPER.readValue(Basics.readFileToString(file), c));
				Logs.add(LogType.INFO, "Loaded new config for " + className, null);
			} catch (Exception e) {
				e.printStackTrace();
				Logs.add(
						LogType.ERROR,
						"Cannot update conf file: " + file.getAbsoluteFile() + "\nClass: " + className,
						e
				);
			}

		}
	}

	public void initAllConfigs() {
		for(final String key : confFileToClass.keySet()) {
			try {
				updateFile(key);
			} catch (Exception e) {}
		}
	}
}
