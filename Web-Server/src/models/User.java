package models;

import content.MongoRequiredField;
import content.MongoSearchField;
import content.MongoTable;
import content.MongoUpdatableField;
import models.fields.UserTypeEnum;

import java.util.Date;

@MongoTable(name="users")
public class User extends BaseObjectEntity {
	@MongoSearchField
	@MongoRequiredField
	private String firstName;

	@MongoSearchField
	@MongoRequiredField
	private String lastName;

	@MongoRequiredField
	private UserTypeEnum type;

	@MongoSearchField
	@MongoRequiredField(unique = true)
	private String username;

	@MongoSearchField
	@MongoRequiredField(unique = true)
	private String email;

	@MongoRequiredField
	@MongoUpdatableField
	private String password;

	private Date lastActive;

	@MongoUpdatableField
	private boolean active;

	public User() {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserTypeEnum getType() {
		return type;
	}

	public void setType(UserTypeEnum type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastActive() {
		return lastActive;
	}

	public void setLastActive() {
		this.lastActive = new Date();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
