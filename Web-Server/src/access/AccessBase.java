package access;

import com.fasterxml.jackson.core.JsonProcessingException;
import content.Basics;
import content.MongoRequiredField;
import content.MongoSearchField;
import content.MongoUpdatableField;
import exceptions.NotImplementedException;
import exceptions.baseEntity.EmptyParameterException;
import exceptions.baseEntity.ObjectNotFoundException;
import exceptions.baseEntity.UsedUniqueKeyException;
import exceptions.baseEntity.WrongTypeException;
import org.bson.types.ObjectId;
import org.jongo.*;
import settings.Config;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.NoSuchObjectException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AccessBase {
	static Jongo jongo= Config.getJongo();
	static SimpleDateFormat yearFormat = new SimpleDateFormat("' ('yyyy')'");

	protected Class<?> entity;
	protected final String collectionName;
	protected MongoCollection jongoCollection;

	//region constructors

	public AccessBase(Class<?> entityClass, String collectionName) {
		this.entity = entityClass;
		this.collectionName = collectionName;
		jongoCollection = jongo.getCollection(collectionName);
	}

	public AccessBase(Class<?> entityClass) {
		this(entityClass, Basics.getCollectionName(entityClass));
	}

	//endregion
	//region get objects in range functions

	public <T> List<T> getInRange(int start, int limit, HttpServletRequest request, boolean orderInAsc) {
		return Basics.iteratorToList(jongoCollection.find().sort("{_id: " + (orderInAsc ? "1" : "-1") +  "}")
				.skip(start).limit(limit).as(entity));
	}

	public <T> List<T> getInRange(int start, int limit, HttpServletRequest request) {
		return getInRange(start, limit, request, true);
	}

	public String getInRangeString(int start, int limit, HttpServletRequest request) throws IOException {
		return Basics.listToJsonString(getInRange(start, limit, request));
	}

	//endregion
	//region get single object functions

	public <T> T getSingleObject(Find find, Class castTo) throws ObjectNotFoundException {
		MongoCursor<?> cursor = find.limit(1).as(castTo);
		if(cursor.count() == 0)
			throw new ObjectNotFoundException();
		return (T) cursor.next();
	}

	public <T> T getSingleObject(Find find) throws ObjectNotFoundException {
		return getSingleObject(find, entity);
	}

	public <T> T getByID(String id) throws ObjectNotFoundException {
		return getByID(new ObjectId(id));
	}

	<T> T getByID(ObjectId id) throws ObjectNotFoundException {
		try {
			return getSingleObject(jongoCollection.find("{_id: #}", id));
		} catch (IllegalArgumentException e) {
			throw new ObjectNotFoundException();
		}
	}

	<T> T getByID(String id, Class castTo) throws ObjectNotFoundException {
		return getByID(new ObjectId(id), castTo);
	}

	<T> T getByID(ObjectId id, Class castTo) throws ObjectNotFoundException {
		return getByID(id, castTo, Basics.getCollectionName(castTo));
	}

	<T> T getByID(ObjectId id, Class castTo, String _collectionName) throws ObjectNotFoundException {
		try {
			return getSingleObject(jongo.getCollection(_collectionName).find("{_id: #}", id), castTo);
		} catch (IllegalArgumentException e) {
			throw new ObjectNotFoundException();
		}
	}

	public String getByIDString(String id) throws ObjectNotFoundException {
		return Basics.serializeObject(getByID(id));
	}

	public String getByIDString(String id, HttpServletRequest request) throws ObjectNotFoundException {
		return getByIDString(id);
	}

	//endregion
	//region search functions

	public <T> List<T> search(String query) {
		query = "'.*" + Basics.escapeSingleQuote(query) + ".*'";

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{$or: [");

		for (Field field : Basics.getAllInheritedPrivateFields(entity)) {
			MongoSearchField mongoSearchField = field.getAnnotation(MongoSearchField.class);
			if(mongoSearchField != null) {
				stringBuilder.append('{');
				stringBuilder.append(field.getName());
				stringBuilder.append(": {$regex: ");
				stringBuilder.append(query);
				stringBuilder.append("}},");
			}
		}
		stringBuilder.setLength(stringBuilder.length() - 1);
		stringBuilder.append("]}");

		return Basics.iteratorToList(jongoCollection.find(stringBuilder.toString()).sort("{ _id: 1 }").limit(10).as(entity));
	}

	public <T> String searchResults(String query) throws NoSuchObjectException {
		List<T> data = search(query);

		if(data.size() == 0)
			throw new NoSuchObjectException("catalog: " + collectionName + "\nquery: " + query);

		try {
			return Basics.listToJsonString(data);
		} catch (IOException e) {
			if(Config.getConfigWebApp().isDeveloperMode())
				e.printStackTrace();
		}
		return "[]";
	}

	//endregion
	//region check object existing, unique for required and unique fields

	public boolean checkObjectForExisting (Object uniqueKey) {
		try {
			return !Basics.isObjectEmpty(getByID(uniqueKey.toString()));
		} catch (ObjectNotFoundException e) {
			if(Config.getConfigWebApp().isDeveloperMode())
				e.printStackTrace();
		}

		return false;
	}

	public <T> T checkObjectForUnique(T object)
			throws EmptyParameterException, UsedUniqueKeyException, IllegalAccessException {

		boolean hasUnique = false;
		Aggregate agg = jongoCollection.aggregate("{$sort: { _id: 1}}");

		for (Field field : Basics.getAllInheritedPrivateFields(entity)) {
			MongoRequiredField mongoRequiredField = field.getAnnotation(MongoRequiredField.class);

			if(mongoRequiredField != null) {
				field.setAccessible(true);
				Object value = field.get(object);
				if(value == null || value.equals(""))
					throw new EmptyParameterException(field.getName());

				if(mongoRequiredField.unique()) {
					hasUnique = true;
					agg = agg.and("{ $match: {" + field.getName() + ": #}}", field.get(object));
				}
			}
		}

		if(hasUnique) {
			if(agg.as(entity).hasNext())
				throw new UsedUniqueKeyException();
		}

		return object;
	}

	//endregion
	//region object creation, updating and saving to db

	public <T> T createNewObject(HttpServletRequest request)
			throws NotImplementedException, EmptyParameterException, WrongTypeException, UsedUniqueKeyException,
			JsonProcessingException, IllegalAccessException {

		return checkObjectForUnique((T) Basics.requestToObject(request, entity));
	}

	public <T> void beforeObjectUpdateDataHook(T receivedData, T dataInDB, HttpServletRequest request) throws WrongTypeException {}

	public <T> void updateObjectInDb(HttpServletRequest request)
			throws JsonProcessingException, IllegalAccessException, EmptyParameterException, ObjectNotFoundException {

		T updatedData = (T) Basics.requestToObject(request, entity);
		T objectInDb = getByID(request.getParameter("id"));

		try {
			beforeObjectUpdateDataHook(updatedData, objectInDb, request);
		} catch (WrongTypeException e) {
			if(Config.getConfigWebApp().isDeveloperMode())
				e.printStackTrace();

			throw new EmptyParameterException(e.getMessage() != null ? e.getMessage() : "Problem with updateDataHook");
		}

		for (Field field : Basics.getAllInheritedPrivateFields(entity)) {
			MongoUpdatableField mongoUpdatableField = field.getAnnotation(MongoUpdatableField.class);

			if(mongoUpdatableField != null && mongoUpdatableField.updatable()) {
				field.setAccessible(true);
				Object value = field.get(updatedData);
				if(value == null || value.equals(""))
					if(!mongoUpdatableField.canBeEmpty())
						throw new EmptyParameterException(field.getName());

				field.set(objectInDb, value);
			}
		}

		saveObject(objectInDb);
	}

	protected <T> void saveObject(MongoCollection _jongoCollection, T object) {
		_jongoCollection.save(object);
	}

	public <T> void saveObject(T object) {
		saveObject(jongoCollection, object);
	}

	//endregion
}
