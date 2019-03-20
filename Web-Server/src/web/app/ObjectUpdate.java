package web.app;

import access.AccessBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.EmptyParameterException;
import exceptions.UsedUniqueKeyException;
import head.Basics;
import head.LogType;
import head.Logs;
import org.bson.types.ObjectId;
import settings.Config;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import web.ServletTemplate;
import web.requestInterfaces.Post;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet(value = "/app/ObjectUpdate", name = "ObjectUpdate")
public class ObjectUpdate extends ObjectTemplate implements Post {
	@Override
	public void processPOST(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	                             PrintWriter writer, final ObjectId userID) {

		final ObjectType objType;
		final Object access;

		try {
//			objType = getRequestObjectType(request, response, userID);
//			access = getAccess(objType, userID, response);
		} catch (IllegalArgumentException e) {
			return;
		}

//		if(checkFindObject(response, writer, access))
//			return;

//		try {
//
////			if(access instanceof AccessMove) {
////				System.out.println("AccessMove");
////			}
//
////			Object result = ((Access) access).updateObject(request);
////			writer.write("OK" + (result != null ?
////					(":" + Config.getObjectWriterConfigured().writeValueAsString(result)) : ""));
//
//		} catch (JsonProcessingException e) {
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			writer.write("MissingParameter");
////			Logs.add(LogType.WARNING, "MissingParameter\n" + requestToString(request), e, userID);
//		} catch (NotImplementedException e) {
//			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
//			writer.write("NotImplemented");
////			Logs.add(LogType.WARNING, "NotImplemented\n" + requestToString(request), e, userID);
//		} catch (IllegalAccessException | UsedUniqueKeyException | EmptyParameterException e) {
//			e.printStackTrace();
//		}
	}
}
