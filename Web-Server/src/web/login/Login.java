package web.login;

import access.AccessUser;
import exceptions.userEntity.IncorrectCredentialsException;
import head.LogType;
import head.Logs;
import models.User;
import org.bson.types.ObjectId;
import settings.Config;
import web.ServletTemplate;
import web.authentication.Authenticate;
import web.requestInterfaces.Post;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebServlet(value = "/login/Login", name = "Login")
public class Login extends ServletTemplate implements Post {
	@Override
	public void processPOST(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	                          PrintWriter writer, final ObjectId userID) {

		final String username = request.getParameter("username");

		try {
			final String tokenName = Config.getConfigWebApp().getTokenName();
			String token = request.getHeader(tokenName);
			if(token == null || token.isEmpty()) {
				AccessUser au = new AccessUser();
				User user = au.login(request.getParameter("username"), request.getParameter("password"));
				token = Authenticate.generateToken(user);
				response.setHeader(tokenName, token);
				writer.write("OK");
				Logs.add(LogType.INFO, "Logged In " + user.getUsername(), null);
//				CronJobs.onLogin();
			} else {
				writer.write("AlreadyLoggedIn");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

		} catch (IncorrectCredentialsException e) {
			Logs.add(LogType.INFO, e.toString() + " with username: " + username, e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
