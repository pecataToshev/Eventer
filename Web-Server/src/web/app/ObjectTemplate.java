package web.app;

import content.Basics;
import head.LogType;
import head.Logs;
import org.bson.types.ObjectId;
import settings.Config;
import web.ServletTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ObjectTemplate extends ServletTemplate {
	protected static final String MISSING = "--missing--";

	protected static boolean checkFindObject(HttpServletResponse response, PrintWriter writer, Object check) {
		if(Basics.isObjectEmpty(check)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			writer.write("NoSuchObject");
			return true;
		}
		return false;
	}

	protected static ObjectType getRequestObjectType(HttpServletRequest request, HttpServletResponse response, final ObjectId userID)
			throws IllegalArgumentException {

		String requestParameterName = MISSING;
		String requestObjectName = MISSING;
		try {

			requestParameterName = Config.getConfigWebApp().getRequestObjectName();
			requestObjectName =  request.getParameter(requestParameterName);
			return ObjectType.valueOf(requestObjectName);

		} catch (IllegalArgumentException | NullPointerException e) {
			Logs.add(
					LogType.WARNING,
					"Cannot convert request.\nrequestParameterName: " + requestParameterName
							+ "\nrequestedObjectString: " + requestObjectName,
					e,
					userID
			);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	protected static Object getAccess(final ObjectType requestedObject, final ObjectId userID, HttpServletResponse response)
			throws IllegalArgumentException {

//		if(requestedObject == ObjectType.MOVE) {
//			return new AccessMove();
//		} else if(requestedObject == ObjectType.PERFORMANCE) {
//			return new AccessPerformance();
//		} else {
//			Logs.add(LogType.WARNING, "Cannot match requestedObject " + requestedObject, null, userID);
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			throw new IllegalArgumentException();
//		}
		return null;

	}
}
