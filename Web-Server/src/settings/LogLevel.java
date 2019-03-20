package settings;

import java.util.HashMap;
import java.util.Map;

public enum LogLevel {
	/**
	 * Developer -> INFO -> SUCCESS -> WARNING -> ERROR -> NONE
	 */
	DEVELOPER(5),
	INFO(4),
	SUCCESS(3),
	WARNING(2),
	ERROR(1),
	NONE(0);

	private int value;
	private static Map map = new HashMap<>();

	private LogLevel(int value) {
		this.value = value;
	}

	static {
		for (LogLevel logLevel : LogLevel.values()) {
			map.put(logLevel.value, logLevel);
		}
	}

	public static LogLevel valueOf(int logLevel) {
		return (LogLevel) map.get(logLevel);
	}

	public int getValue() {
		return value;
	}
}
