package watchersCronsFilters;

import models.User;
import settings.AppRequestAttributes;
import settings.Config;
import web.authentication.Authenticate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = "/*", filterName = "Request Filter")
public class Filter implements javax.servlet.Filter {
	private static final String login = "/login";
	private static final String app = "/app";

	private void forbidden(HttpServletResponse response, String path, boolean loggedIn) throws IOException {
		if(Config.getConfigWebApp().isDeveloperMode()) {
			System.out.println("FORBIDDEN " + path);
		}
		response.setStatus(loggedIn ? HttpServletResponse.SC_FORBIDDEN : HttpServletResponse.SC_UNAUTHORIZED);
		response.sendRedirect(Config.getConfigWebApp().getUrlPrefix() + login);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		// region set request and response encoding
		request.setCharacterEncoding(Config.getConfigWebApp().getEncoding());
		response.setCharacterEncoding(Config.getConfigWebApp().getEncoding());
		// endregion

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		final String path = httpRequest.getServletPath();

		// region remove cache for development
		if(Config.getConfigWebApp().isDeveloperMode()) {
			httpResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			httpResponse.setHeader("Pragma", "no-cache");
		}
		// endregion

		// region CORS support
		{
			final String origin = httpRequest.getHeader("Origin");
			if(origin != null && !origin.isEmpty()) {
				final String domain = origin.substring(origin.indexOf("://") + 3);
				if(Config.getConfigWebApp().isCrossDomainAllowed(domain)) {
					httpResponse.setHeader("Access-Control-Allow-Origin", origin);
					httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
					httpResponse.setHeader("Access-Control-Allow-HttpMethods", "GET, POST, PUT, DELETE, HEAD");
					httpResponse.setHeader("Access-Control-Max-Age", "1209600");
					httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//					httpResponse.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
				}
			}
		}
		// endregion

		//region check is user logged in
		boolean isLoggedIn = Authenticate.isLoggedIn(httpRequest);
		User user = null;
		if(isLoggedIn) {
			user = Authenticate.getLoggedUserData(httpRequest);
		}
		//endregion

		// region Filter traffic
		if(path.startsWith(app)) {
			if(!isLoggedIn) {
				forbidden(httpResponse, path, false);
				return;
			}
		} else if(path.startsWith("/all")) {
			// always allow
		} else if(path.startsWith("/private")) {
			forbidden(httpResponse, path, true);
			return;
		} else if(path.startsWith(login)) {
			if(isLoggedIn) {
				httpResponse.sendRedirect(Config.getConfigWebApp().getUrlPrefix() + app);
				return;
			}
		} else {
			forbidden(httpResponse, path, isLoggedIn);
			return;
		}
		// endregion

		// region set attributes to request
		if(user != null) {
			request.setAttribute(AppRequestAttributes.LOGGED_IN, isLoggedIn);
			request.setAttribute(AppRequestAttributes.IS_ADMIN, false/*user.getRole() == UserRole.ADMIN*/);
			request.setAttribute(AppRequestAttributes.USER_ID, user.getId());
		}
		// endregion

		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}
}
