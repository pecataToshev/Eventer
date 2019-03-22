package settings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.FileReadingException;
import exceptions.FileWatcherAddingNewPathException;
import exceptions.FileWathcherNotInitialisedException;
import head.LogType;
import head.Logs;
import watchersCronsFilters.ConfigWatcher;
import web.authentication.RandomString;
import org.jongo.Jongo;

import java.nio.file.Path;

public class Config {
	public static final int MAX_LENGTH_OF_ACCESS_CODE = 32;
	public static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	public static final long TIMESTAMP_CREATION_TIME = System.currentTimeMillis();
	public static final RandomString RANDOM_STRING = new RandomString();

	private static ConfigWebApp configWebApp = new ConfigWebApp();
	private static String urlPrefix = "/DanceWriter";
	private static ConfigWatcher configWatcher = new ConfigWatcher(TIMESTAMP_CREATION_TIME);
	private static Dirs dirs = new Dirs();
	private static Jongo jongo = null;


	// region static getters
	public static ConfigWebApp getConfigWebApp() {
		return configWebApp;
	}

	public static Dirs getDirs() {
		return dirs;
	}

	public static Jongo getJongo() {
		return jongo;
	}

	public static String getUrlPrefix() {
		return urlPrefix;
	}

	public static ObjectWriter getObjectWriterConfigured() {
		if(Config.getConfigWebApp().isDeveloperMode()) {
			return MAPPER.writerWithDefaultPrettyPrinter();
		}
		return MAPPER.writer();
	}
	// endregion



	public void setConfigWebApp(ConfigWebApp configWebAppNew) throws FileReadingException {
		configWebAppNew.setUrlPrefix();
		configWebApp = configWebAppNew;
	}

	public void setDBCredentials(DBCredentials dbCredentials) {
		jongo = dbCredentials.createJongo();
	}

	public void addConfigWatcherDir(Path path) throws FileWathcherNotInitialisedException, FileWatcherAddingNewPathException {
		configWatcher.addWatcher(path);
		Logs.add(LogType.INFO, "Added new dir to file watcher", null);
	}

	public void runFileWatcher() {
		configWatcher.start();
	}

	public void setDirs(String path) {
		dirs = new Dirs(path);
	}

	public void setUrlPrefix(String urlPrefix) {
		Config.urlPrefix = urlPrefix;
	}
}
