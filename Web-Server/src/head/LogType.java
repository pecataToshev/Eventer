package head;

import java.util.HashMap;
import java.util.Map;

public enum LogType {
	INFO(4),
	SUCCESS(3),
	WARNING(2),
	ERROR(1);

	private int value;
	private static Map map = new HashMap<>();

	private LogType(int value) {
		this.value = value;
	}

	static {
		for (LogType logType : LogType.values()) {
			map.put(logType.value, logType);
		}
	}

	public static LogType valueOf(int logType) {
		return (LogType) map.get(logType);
	}

	public int getValue() {
		return value;
	}
}
