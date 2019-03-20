package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import content.MongoSearchField;
import content.MongoUpdatableField;
import org.bson.types.ObjectId;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity {
	@MongoSearchField
	@MongoUpdatableField(updatable = false)
	private ObjectId _id;

	public String getId() {
		return _id.toString();
	}

	public void resetId() {
		_id = null;
	}

	public ObjectId id() {
		return _id;
	}
}
