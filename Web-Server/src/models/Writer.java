package models;

import content.MongoTable;
import models.fields.WriterStatusEnum;

@MongoTable(name="writer")
public class Writer extends BaseObjectEntity {
	private String key;
	private String value;
	private WriterStatusEnum status;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public WriterStatusEnum getStatus() {
		return status;
	}

	public void setStatus(WriterStatusEnum status) {
		this.status = status;
	}
}
