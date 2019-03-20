package head;

import access.AccessLog;
import models.Log;
import org.bson.types.ObjectId;
import settings.Config;

public class Logs {
	private static AccessLog accessLog = null;
	private static Log log = new Log();
	private static boolean isDBReady = false;

	public static final synchronized void add(LogType type, String key, Throwable e) {
		add(type, key, e, null);
	}

	public static final synchronized void add(LogType type, String key, Throwable e, final ObjectId userID) {
		if(Config.getConfigWebApp().getLogLevel().getValue() < type.getValue()) {
			// skip log if not included in log level
			return;
		}

		if(!isDBReady){
			if(accessLog == null && Config.getJongo() != null) {
				accessLog = new AccessLog();
				isDBReady = true;
			}
		}

		log.reset(type, key, e, userID);
		if(Config.getConfigWebApp().isAddLogsInDB() && isDBReady) {
			try {

				accessLog.saveObject(log);

			} catch (Exception e1) {}
		}

		if(Config.getConfigWebApp().isDeveloperMode()) {
			System.err.println("\nLOG:\nType: " + type + "\nKey: " + key + "\nUserID: " + userID + "\n");
			if(e != null) {
				e.printStackTrace();
			}
		}
	}
}
