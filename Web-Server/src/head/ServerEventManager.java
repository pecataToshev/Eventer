package head;

import exceptions.FileWatcherAddingNewPathException;
import exceptions.FileWathcherNotInitialisedException;
import settings.Config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ServerEventManager implements ServletContextListener,
		HttpSessionListener, HttpSessionAttributeListener {

	// Public constructor is required by servlet spec
	public ServerEventManager() {
	}

	// -------------------------------------------------------
	// ServletContextListener implementation
	// -------------------------------------------------------
	public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */

		// region init default dirs
        ServletContext servletContext = sce.getServletContext();
		Config conf = new Config();
		conf.setDirs(servletContext
				.getInitParameter("DataPath" + (System.getProperty("os.name")
						.contains("Windows") ? "Win" : "Linux"))
		);
		// endregion

		// region set app url prefix
		conf.setUrlPrefix(sce.getServletContext().getContextPath());
		// endregion

		// region start file watcher for config files
		try {
			conf.addConfigWatcherDir(Config.getDirs().getConf().toPath());
		} catch (FileWathcherNotInitialisedException | FileWatcherAddingNewPathException e) {
			Logs.add(LogType.ERROR, "Cannot access conf dir", e);
		}
		conf.runFileWatcher();
		// endregion

		// region init all config files
		UpdateConfigFiles updateConfigFiles = new UpdateConfigFiles();
		updateConfigFiles.initAllConfigs();
		// endregion
	}

	public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
	}

	// -------------------------------------------------------
	// HttpSessionListener implementation
	// -------------------------------------------------------
	public void sessionCreated(HttpSessionEvent se) {
		/* Cookies is created. */
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		/* Cookies is destroyed. */
	}

	// -------------------------------------------------------
	// HttpSessionAttributeListener implementation
	// -------------------------------------------------------

	public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
	}

	public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
	}

	public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
	}
}
