package models;

import content.MongoTable;
import head.Basics;
import head.LogType;
import org.bson.types.ObjectId;

@MongoTable(name="logs")
public class Log extends BaseObjectEntity {
	private String message;
	private String stackTrace;
	private LogType type;
	private ObjectId user;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public LogType getType() {
		return type;
	}

	public void setType(LogType type) {
		this.type = type;
	}

	public void reset(LogType type, String message) {
		reset(type, message, null);
	}
	public void reset(LogType type, String message, Throwable e) {
		reset(type, message, e, (ObjectId) null);
	}
	public void reset(LogType type, String message, Throwable e, String userID) {
		reset(type, message, e, (userID != null ? new ObjectId(userID) : null));
	}
	public void reset(LogType type, String message, Throwable e, ObjectId userID) {
		resetId();
		setNowCreationTime();
		this.message = message;
		this.type = type;
		this.stackTrace = Basics.throwableToString(e);
		this.user = userID;
	}
}
