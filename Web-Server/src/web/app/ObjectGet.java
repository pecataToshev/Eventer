package web.app;

import org.bson.types.ObjectId;
import exceptions.RequestProcessingException;
import settings.Config;
import web.requestInterfaces.Get;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet(value = "/app/ObjectGet", name = "ObjectGet")
public class ObjectGet extends ObjectTemplate implements Get {
	@Override
	public void processGET(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			PrintWriter writer,
			final ObjectId userID
	) throws RequestProcessingException {

		final ObjectType requestedObject;
		try {
			requestedObject = getRequestObjectType(request, response, userID);
		} catch (IllegalArgumentException e) {
			return;
		}

		Object access;
		try {
			access = getAccess(requestedObject, userID, response);
		} catch (IllegalArgumentException e) {
			return;
		}

		final String objectCode = request.getParameter("code");
		try {
//			writer.write("OK:" + Config.getObjectWriterConfigured().writeValueAsString(
//					((AccessDBObject) access).getByUnique(objectCode))
//			);
		} catch (Exception e) {
			throw new RequestProcessingException("Cannot convert object to json.", e);
		}

	}
}
