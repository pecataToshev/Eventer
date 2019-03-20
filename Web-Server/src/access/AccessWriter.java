package access;

import exceptions.baseEntity.ObjectNotFoundException;
import models.Writer;

public class AccessWriter extends AccessBase {
	public AccessWriter() {
		super(Writer.class);
	}

	public Writer check(String text) throws ObjectNotFoundException {
		return getSingleObject(jongoCollection.find("{key: #}", text));
	}
}
