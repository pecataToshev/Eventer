package web.login;

import access.AccessUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.NotImplementedException;
import exceptions.baseEntity.EmptyParameterException;
import exceptions.baseEntity.UsedUniqueKeyException;
import exceptions.baseEntity.WrongTypeException;
import org.bson.types.ObjectId;
import web.ServletTemplate;
import web.requestInterfaces.Post;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet(value = "/login/Register", name = "Register")
public class Register extends ServletTemplate implements Post {

	@Override
	public void processPOST(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter writer, ObjectId userID) {
		AccessUser au = new AccessUser();
		try {
			au.createNewObject(request);
			Login login = new Login();
			login.processPOST(request, response, session, writer, userID);
		} catch (NotImplementedException | IllegalAccessException | JsonProcessingException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

		} catch (UsedUniqueKeyException e) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			if(e.getMessage() != null)
				writer.write(e.getMessage());

		} catch (EmptyParameterException | WrongTypeException e) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			if(e.getMessage() != null)
				writer.write(e.getMessage());
		}
	}
}
