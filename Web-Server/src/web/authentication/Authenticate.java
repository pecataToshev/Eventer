package web.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import models.User;
import org.bson.types.ObjectId;
import settings.Config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Authenticate {
	private static Map<String, String> tokenKeys = new HashMap<>();
	public static class ClaimsKeys {
		public static final String firstName = "firstName";
		public static final String lastName = "lastName";
		public static final String gender = "gender";
		public static final String role = "role";
	}

	/**
	 * Gets token id form token keyID.
	 *
	 * @param jwt Token to get id.
	 *
	 * @return Id of token cats to Integer.
	 */
	public static String getTokenId(DecodedJWT jwt) {
		return jwt.getKeyId();
	}

	/**
	 * Return token string from cookie.
	 *
	 * @param request HttpServletRequest with cookie.
	 *
	 * @return Token string or NULL.
	 */
	public static String getTokenStringFromCookie(HttpServletRequest request) {
		final String name = Config.getConfigWebApp().getTokenName();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Generates token with password for user.
	 *
	 * @param user User object (JPA) from DB.
	 * @return Token string.
	 */
	public static String generateToken(User user) {
		// region generate secret
		final String secret;
		{
			// generate two codes and combine them
			final String s1 = Config.RANDOM_STRING.nextString();
			final String s2 = Config.RANDOM_STRING.nextString();
			StringBuilder sb = new StringBuilder();
			int len = s1.length();
			for(int i = 0;  i < len;  i++) {
				sb.append(s1.charAt(i));
				sb.append(s2.charAt(i));
			}
			secret = sb.toString();
		}
		// endregion

		Algorithm algorithm = Algorithm.HMAC256(secret);
		tokenKeys.put(user.getId(), secret);
		return JWT.create()
				.withIssuer(user.getEmail())
				.withKeyId(user.getId())
				.withClaim(ClaimsKeys.firstName, user.getFirstName())
				.withClaim(ClaimsKeys.lastName, user.getLastName())
				.sign(algorithm);
	}

	/**
	 * Gets token string and parses it.
	 *
	 * @param token Token string.
	 *
	 * @return Decoded token;
	 *
	 * @throws NullPointerException      when token is null
	 * @throws JWTVerificationException  when secret is incorrect
	 * @throws IllegalArgumentException  when argument is missing
	 */
	public static DecodedJWT decodeToken(String token)
			throws NullPointerException, JWTVerificationException, IllegalArgumentException {

		DecodedJWT jwt = JWT.decode(token);
		Algorithm algorithm = Algorithm.HMAC256(tokenKeys.get(getTokenId(jwt)));
		return JWT.require(algorithm)
				.withIssuer(jwt.getIssuer())
				.build()
				.verify(token);
	}

	/**
	 * Gets user data stored in token.
	 *
	 * @param token Token string.
	 *
	 * @return models.User with filled data.
	 *
	 * @throws NullPointerException      when token is null
	 * @throws JWTVerificationException  when secret is incorrect
	 * @throws IllegalArgumentException  when argument is missing
	 */
	public static User getLoggedUserData(String token)
			throws NullPointerException, JWTVerificationException, IllegalArgumentException {

		DecodedJWT decoded = decodeToken(token);
		User user = new User();
		user.setEmail(decoded.getIssuer());
		user.setFirstName(decoded.getClaim(ClaimsKeys.firstName).asString());
		user.setLastName(decoded.getClaim(ClaimsKeys.lastName).asString());
		//user.setGender(decoded.getClaim(ClaimsKeys.gender).asBoolean());
		//user.setRole(UserRole.valueOf(decoded.getClaim(ClaimsKeys.role).asString()));

		try {
			Field id = models.BaseEntity.class.getDeclaredField("id");
			id.setAccessible(true);
			id.set(user, decoded.getKeyId());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * Gets user data stored in token, not converted from request.
	 *
	 * @param request Current request.
	 *
	 * @return models.User with filled data.
	 *
	 * @throws NullPointerException      when token is null
	 * @throws JWTVerificationException  when secret is incorrect
	 * @throws IllegalArgumentException  when argument is missing
	 */
	public static User getLoggedUserData(HttpServletRequest request) {
		return getLoggedUserData(getTokenStringFromCookie(request));
	}

	/**
	 * Check is user logged in via token.
	 *
	 * @param token Token string from cookie (Can handle NULL).
	 *
	 * @return Is token valid and user logged.
	 */
	public static boolean isLoggedIn(String token) {
		try {
			decodeToken(token);
			return true;
		} catch (NullPointerException | JWTVerificationException | IllegalArgumentException e) {}

		return false;
	}

	/**
	 * Gets token from request and check is user logged in.
	 *
	 * @param request Current HttpServletRequest.
	 *
	 * @return Uses {@link #isLoggedIn(String) isLoggedIn} method.
	 */
	public static boolean isLoggedIn(HttpServletRequest request) {
		return isLoggedIn(getTokenStringFromCookie(request));
	}


	/**
	 * Gets logged user id.
	 *
	 * @param request Current HttpServletRequest.
	 *
	 * @return Logged user id or 0 if not logged in.
	 */
	public static ObjectId getLoggedUserId(HttpServletRequest request) {
		try {
			return new ObjectId(getTokenId(decodeToken(getTokenStringFromCookie(request))));
		} catch (Exception e ){
			return null;
		}
	}

	/**
	 * Removes authentication key for user.
	 * @param userID ID of user which key to be removed.
	 */
	private static void removeAuthenticationKeyForUserWithDisabledStatus(String userID) {
		tokenKeys.put(userID, null);
	}
}
