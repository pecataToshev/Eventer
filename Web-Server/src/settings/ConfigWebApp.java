package settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigWebApp {
	static final String appVersion = "0.1";
	static final String tokenName = "Eventer";
	static final String requestObjectName = "requestObjectName";
	private String confVersion = "0.0";
	private String encoding = "UTF-8";
	private boolean developerMode = false;
	private boolean addLogsInDB = false;
	private boolean addLogsInTxt = false;
	private boolean addLogsInHtml = false;
	private LogLevel logLevel = LogLevel.ERROR;
	private String urlPrefix = "";
	private ArrayList<String> allowCORSFrom = new ArrayList<>();

	// region getters
	public String getAppVersion() {
		return appVersion;
	}

	public String getTokenName() {
		return tokenName;
	}

	public String getRequestObjectName() {
		return requestObjectName;
	}

	public String getConfVersion() {
		return confVersion;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isDeveloperMode() {
		return developerMode;
	}

	public boolean isAddLogsInDB() {
		return addLogsInDB;
	}

	public boolean isAddLogsInTxt() {
		return addLogsInTxt;
	}

	public boolean isAddLogsInHtml() {
		return addLogsInHtml;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public ArrayList<String> getAllowCORSFrom() {
		return allowCORSFrom;
	}

	public boolean isCrossDomainAllowed(final String domain) {
		return allowCORSFrom.contains(domain);
	}
	// endregion

	void setUrlPrefix() {
		this.urlPrefix = Config.getUrlPrefix();
	}
}
