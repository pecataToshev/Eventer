package web;

import head.LogType;
import head.Logs;
import org.bson.types.ObjectId;
import settings.Config;
import web.authentication.Authenticate;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.*;

public class ServletTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static enum HttpMethods {
		DELETE,
		GET,
		HEAD,
		OPTIONS,
		POST,
		PUT,
		TRACE
	}

	public static String requestToString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		sb.append("Request...\n\tMethod: ");
		sb.append(request.getMethod());

		// region headers
		sb.append("\n\tHeaders List");
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			final String headerName = headerNames.nextElement();
			sb.append("\n\t\tHeader ");
			sb.append(headerName);
			sb.append(" - ");
			sb.append(request.getHeader(headerName));
		}
		// end region

		// region params
		sb.append("\n\n\tParams List");
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			final String paramName = params.nextElement();
			sb.append("\n\t\tParameter ");
			sb.append(paramName);
			sb.append(" - ");
			sb.append(request.getParameter(paramName));
		}
		// endregion

		// region add parts
		/*
		try {
			sb.append("\n\n\tParts List");
			for (Part part : request.getParts()) {
				sb.append("\n\t");
				sb.append(part.getName());
				sb.append(":\n\t\t");
				sb.append(part.getContentType());
				sb.append("\n\t\t");
				sb.append(Basics.readInputStream(part.getInputStream()));
			}
		} catch (Exception e) {}
*/
		// endregion
		sb.append("\nRequestEnd");

		return sb.toString();
	}

	public ServletTemplate() { super(); }

	// region override methods
	@Override
	protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.DELETE, request, response);
	}

	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.GET, request, response);
	}

	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.HEAD, request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.OPTIONS, request, response);
	}

	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.POST, request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.PUT, request, response);
	}

	@Override
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException {
		simpler(HttpMethods.TRACE, request, response);
	}
	// endregion

	private void simpler(HttpMethods httpMethod, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		PrintWriter writer = response.getWriter();

		response.setHeader("content-type", "text/plain; charset=" + Config.getConfigWebApp().getEncoding());
		final ObjectId userID = Authenticate.getLoggedUserId(request);


		try {
			Method method = this.getClass().getDeclaredMethod(
					"process" + httpMethod.toString(),
					HttpServletRequest.class,
					HttpServletResponse.class,
					HttpSession.class,
					PrintWriter.class,
					Integer.class
			);

			method.invoke(this, request, response, session, writer, userID);
		} catch (Throwable e) {
			if(e instanceof NoSuchMethodException) {
				writeError(writer, response, httpMethod);
			}
			Logs.add(
					LogType.WARNING,
					"Cannot process request:\n" + requestToString(request),
					e,
					userID
			);
		}

		writer.close();
	}

	private void writeError(PrintWriter writer, HttpServletResponse response, HttpMethods method) {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		writer.write("NOT IMPLEMENTED SERVLET REQUEST TYPE [" + method + "]");
	}
}
