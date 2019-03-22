package web.app;

import access.AccessBase;
import content.Basics;
import org.bson.types.ObjectId;
import exceptions.RequestProcessingException;
import settings.AppRequestAttributes;
import settings.Config;
import web.requestInterfaces.Get;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet(value = "/app/ObjectGetList", name = "ObjectGetList")
public class ObjectGetList extends ObjectTemplate implements Get {
	@Override
	public void processGET(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			PrintWriter writer,
			final ObjectId userID
	) throws RequestProcessingException {

		final ObjectType requestedObject;
		Object access;
		try {
			requestedObject = getRequestObjectType(request, response, userID);
			access = getAccess(requestedObject, userID, response);
		} catch (IllegalArgumentException e) {
			return;
		}

		// region get offset and limit
		int start = 0;
		int limit = 50;
		String tmp = request.getParameter("start");
		if(!Basics.isObjectEmpty(tmp)) {
			try {
				start = Integer.parseInt(tmp);
			} catch (Exception e) {}
		}
		tmp = request.getParameter("limit");
		if(!Basics.isObjectEmpty(tmp)) {
			try {
				limit = Integer.parseInt(tmp);
			} catch (Exception e) {}
		}
		// endregion

		try {
			writer.write("OK:" + Config.getObjectWriterConfigured().writeValueAsString(
					((AccessBase) access).getInRange(start, limit, request/*(Integer) request.getAttribute(AppRequestAttributes.USER_ID)*/))
			);
		} catch (Exception e) {
			throw new RequestProcessingException("Cannot convert object to json.", e);
		}

	}
}
