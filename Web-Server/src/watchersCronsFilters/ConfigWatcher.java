package watchersCronsFilters;

import exceptions.FileWatcherAddingNewPathException;
import exceptions.FileWathcherNotInitialisedException;
import head.LogType;
import head.Logs;
import head.UpdateConfigFiles;
import settings.Config;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigWatcher extends Thread {
	private static boolean isRunning = false;
	private WatchService watchService = null;
	private final long timestamp;
	private Map<String, Long> lastModified = new HashMap<>();
	private Config config = new Config();

	public ConfigWatcher(long timestamp) {
		this.timestamp = timestamp;
	}

	public void run() {
		if(isRunning)
			return;

		isRunning = true;
		while (true) {
			try {
				final WatchKey wk = watchService.take();

				for (WatchEvent<?> event : wk.pollEvents()) {
					// remove is this thread is too old
					if(timestamp != Config.TIMESTAMP_CREATION_TIME) {
						return;
					}

					UpdateConfigFiles.updateFile(event.context().toString());
				}

				// reset current event to be able to handle new one
				wk.reset();

			} catch (InterruptedException e) {
				Logs.add(
						LogType.ERROR,
						"Cannot execute file watcher",
						e
				);
			}
		}
	}

	{
		try {
			watchService = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			Logs.add(LogType.ERROR, "Cannot create watch service", e);
		}
	}

	public void addWatcher(Path path) throws FileWatcherAddingNewPathException, FileWathcherNotInitialisedException {
		if(watchService == null)
			throw new FileWathcherNotInitialisedException();

		try {
			path.register(
					watchService,
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			Logs.add(LogType.ERROR, "Cannot add path to watch service\nPath: " + path.toString(), e);
			throw new FileWatcherAddingNewPathException();
		}
	}
}
