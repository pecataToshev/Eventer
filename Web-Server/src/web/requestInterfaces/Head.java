package web.requestInterfaces;

import org.bson.types.ObjectId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public interface Head {
	void processHEAD(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	                   PrintWriter writer, final ObjectId userID);
}
