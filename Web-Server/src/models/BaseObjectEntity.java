package models;

import java.util.Date;

public class BaseObjectEntity extends BaseEntity {
	private Date creationTime;

	public Date getCreationTime() {
		return creationTime;
	}

	public void setNowCreationTime () {
		creationTime = new Date();
	}
}
