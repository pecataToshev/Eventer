package web.app;

import access.AccessBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.NotImplementedException;
import exceptions.baseEntity.EmptyParameterException;
import exceptions.baseEntity.UsedUniqueKeyException;
import exceptions.baseEntity.WrongTypeException;
import head.LogType;
import head.Logs;
import org.bson.types.ObjectId;
import settings.Config;
import web.requestInterfaces.Post;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@MultipartConfig
@WebServlet(value = "/app/ObjectNew", name = "ObjectNew")
public class ObjectNew extends ObjectTemplate implements Post {
	@Override
	public void processPOST(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	                             PrintWriter writer, final ObjectId userID) {

		final ObjectType objType;
		final Object access;

		try {

			objType = getRequestObjectType(request, response, userID);
			access = getAccess(objType, userID, response);

		} catch (IllegalArgumentException e) {
			return;
		}

		if(checkFindObject(response, writer, access))
			return;

		try {

			Object result = ((AccessBase) access).createNewObject(request);
			writer.write("OK" + (result != null ?
					(":" + Config.getObjectWriterConfigured().writeValueAsString(result)) : ""));

		} catch (EmptyParameterException | JsonProcessingException | IllegalAccessException | WrongTypeException | NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			writer.write("MissingParameter " + (e.getMessage() != null ? e.getMessage() : ""));
			Logs.add(LogType.WARNING, "MissingParameter\n" + requestToString(request), e, userID);
		} catch (NotImplementedException e) {
			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			writer.write("NotImplemented");
			Logs.add(LogType.WARNING, "NotImplemented\n" + requestToString(request), e, userID);
		} catch (UsedUniqueKeyException e) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			if(e.getMessage() != null)
				writer.write(e.getMessage());

			Logs.add(LogType.WARNING, "UsedUniqueKeyException\n" + requestToString(request), e, userID);
		}
	}
}
