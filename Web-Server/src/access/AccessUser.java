package access;

import com.fasterxml.jackson.core.JsonProcessingException;
import content.Basics;
import exceptions.NotImplementedException;
import exceptions.baseEntity.EmptyParameterException;
import exceptions.baseEntity.ObjectNotFoundException;
import exceptions.baseEntity.UsedUniqueKeyException;
import exceptions.baseEntity.WrongTypeException;
import exceptions.userEntity.IncorrectCredentialsException;
import models.User;
import org.bson.types.ObjectId;
import settings.Config;
import web.authentication.Authenticate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Date;

public class AccessUser extends AccessBase {
	public AccessUser() {
		super(User.class);
	}

	public User login(String username, String password) throws IncorrectCredentialsException {
		try {
			return getSingleObject(jongoCollection
					.find("{username: #, password: #, active: true}", username, hashPass(password, username)));
		} catch (ObjectNotFoundException e) {
			throw new IncorrectCredentialsException();
		}
	}

	private String hashPass(String password, String salt) {
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest((password + salt).getBytes(Config.getConfigWebApp().getEncoding()));
			StringBuilder hexString = new StringBuilder();

			for(byte aHash : hash) {
				String hex = Integer.toHexString(0xff & aHash);
				if(hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	private void checkPasswordMatch(User user, HttpServletRequest request) throws UsedUniqueKeyException {
		if(!user.getPassword().equals(request.getParameter("rePassword")))
			throw new UsedUniqueKeyException("PasswordsNotMatching");

		user.setPassword(hashPass(user.getPassword(), user.getUsername()));
	}

	@Override
	public <T> T createNewObject(HttpServletRequest request)
			throws NotImplementedException, EmptyParameterException, WrongTypeException, UsedUniqueKeyException,
			JsonProcessingException, IllegalAccessException {

		User user = super.createNewObject(request);

		checkPasswordMatch(user, request);

		user.setActive(true);
		user.setNowCreationTime();
		saveObject(user);

		return null;
	}

	@Override
	public String getByIDString(String id) throws ObjectNotFoundException {
		User user = getByID(id);
		user.setPassword(null);
		return Basics.serializeObject(user);
	}

	@Override
	public <T> void beforeObjectUpdateDataHook(T receivedData, T dataInDB, HttpServletRequest request) throws WrongTypeException {
		User newUserData = (User) receivedData;
		User oldUserData = (User) dataInDB;

		if(newUserData.getPassword() != null) {
			try {
				newUserData.setUsername(oldUserData.getUsername());
				checkPasswordMatch(newUserData, request);
			} catch (UsedUniqueKeyException e) {
				throw new WrongTypeException(e.getMessage());
			}
		}

		if(!newUserData.isActive() && oldUserData.isActive()) {
			try {

				Method removeAuthKey = Authenticate.class.getDeclaredMethod("removeAuthenticationKeyForUserWithDisabledStatus", String.class);
				removeAuthKey.setAccessible(true);
				removeAuthKey.invoke(Authenticate.class, oldUserData.getId());

			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				if(Config.getConfigWebApp().isDeveloperMode())
					e.printStackTrace();
			}
		}

	}

	public void updateLastActive(String id) {
		jongoCollection.update("{_id: #}", new ObjectId(id)).with("{$set: {lastActive: #}}", new Date());
	}
}
