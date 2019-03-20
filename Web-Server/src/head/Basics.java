package head;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.FileReadingException;
import settings.Config;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class Basics {
	// region File and Stream reading
	public static final String readInputStream (InputStream inputStream, String encoding) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString(encoding);
	}

	public static final String readInputStream (InputStream inputStream) throws IOException {
		return readInputStream(inputStream, Config.getConfigWebApp().getEncoding());
	}

	public static final String readFileToString(File file) throws FileReadingException {
		StringBuilder sb = new StringBuilder();

		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
		} catch (IOException e) {
			Logs.add(LogType.ERROR, "problem with file... " + file.getAbsoluteFile(), e);
			throw new FileReadingException();
		}

		return sb.toString();
	}

	public static <T> T requestToObject(HttpServletRequest request, Class<T> objectClass) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		JsonNode node = mapper.createObjectNode();

		Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		while(paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			((ObjectNode) node).put(paramName, request.getParameter(paramName));
		}

		return mapper.treeToValue(node, objectClass);
	}
	// endregion

	public static final String throwableToString(Throwable e) {
		if(e == null)
			return "";

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static final String cutStringToMaxLength(String s, int max) {
		int c = s.length();
		if(c > max)
			return s.substring(0, max);

		return s;
	}

	public static boolean isObjectEmpty(Object o) {
		return o == null || o.equals("");
	}

	public static final List<Field> getAllInheritedPrivateFields(Class aClass) {
		List<Field> fields = new ArrayList<>(Arrays.asList(aClass.getDeclaredFields()));
		while((aClass = aClass.getSuperclass()) != Object.class)
			fields.addAll(Arrays.asList(aClass.getDeclaredFields()));

		return fields;
	}

	public static final String escapeHTML(String s) {
		if(s == null || s.isEmpty() || s.equals(""))
			return "";
		StringBuilder sb = new StringBuilder();
		int len = s.length();
		for(int i = 0;  i < len;  i++) {
			char c = s.charAt(i);
			switch (c) {
				case '>':
					break;

				case '<':
					break;

				case '\'':
					break;

				case '"':
					break;

				case '/':
					break;

				default:
					sb.append(c);
					break;
			}
		}

		return sb.toString();
	}

	public static final String removeNewLineAndTabs(String s) {
		if(s == null || s.isEmpty() || s.equals(""))
			return "";

		StringBuilder sb = new StringBuilder();
		int len = s.length();
		for(int i = 0;  i < len;  i++) {
			char c = s.charAt(i);
			switch (c) {
				case '\r':
					break;

				case '\n':
					break;

				case '\t':
					break;

				default:
					sb.append(c);
					break;
			}
		}

		return sb.toString();
	}
}
