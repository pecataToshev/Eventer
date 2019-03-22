package content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.FileReadingException;
import head.LogType;
import head.Logs;
import models.BaseEntity;
import settings.Config;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Basics {
	public static <T> String listToJsonString(List<T> list, String encoding) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(out, list);
		return new String(out.toByteArray(), encoding);
	}

	public static <T> String listToJsonString(List<T> list) throws IOException {
		return listToJsonString(list, Config.getConfigWebApp().getEncoding());
	}

	public static String getCollectionName(Class<?> entityClass) {
		return entityClass.getAnnotation(MongoTable.class).name();
	}

	public static <T> List<T> iteratorToList(Iterator<?> iterator) {
		List<T> list = new ArrayList<T>();
		while (iterator.hasNext())
			list.add((T) iterator.next());
		return list;
	}

	public static boolean isObjectEmpty(Object o) {
		return o == null || o.equals("");
	}

	public static String escapeSingleQuote(String s) {
		return s.replace("\\'", "'").replace("'", "\\'");
	}

	public static <T> T requestToObject(HttpServletRequest request, Class<T> objectClass) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JsonNode node = mapper.createObjectNode();

		Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		while(paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			((ObjectNode) node).put(paramName, request.getParameter(paramName));
		}

		return mapper.treeToValue(node, objectClass);
	}

	public static <E extends Enum<E>> String enumToJson(Class enumerationClass) {
		final String prefix = "OK:[";
		Enum<E>[] possibleValues = (Enum<E>[]) enumerationClass.getEnumConstants();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(prefix);
		for(Enum<E> type : possibleValues) {
			stringBuilder.append('{');
			stringBuilder.append("\"id\":\"" + type + "\",");
			stringBuilder.append("\"name\":\"" + type + "\"");
			stringBuilder.append("},");
		}
		if(stringBuilder.length() > prefix.length())
			stringBuilder.setLength(stringBuilder.length() - 1);
		stringBuilder.append(']');
		return stringBuilder.toString();
	}

	public static String serializeObject(Object obj, boolean pretty) {
		try {
			return Config.getObjectWriterConfigured().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			if(Config.getConfigWebApp().isDeveloperMode())
				e.printStackTrace();
			String id = null;
			if(obj instanceof BaseEntity)
				id = ((BaseEntity) obj).getId();
			Logs.add(LogType.ERROR, "error on converting object to json string\nid = " + id + "\n", e);
		}

		return null;
	}

	public static String serializeObject(Object obj) {
		return serializeObject(obj, false);
	}

	public static String throwableToString(Throwable e) {
		if(e == null)
			return "";

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static String exceptionToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static List<Field> getAllInheritedPrivateFields(Class aClass) {
		List<Field> fields = new ArrayList<>(Arrays.asList(aClass.getDeclaredFields()));
		while((aClass = aClass.getSuperclass()) != Object.class)
			fields.addAll(Arrays.asList(aClass.getDeclaredFields()));

		return fields;
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
}
