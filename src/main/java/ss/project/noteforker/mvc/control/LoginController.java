package ss.project.noteforker.mvc.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.model.domain.User;
import ss.project.noteforker.service.json.JsonService;


@SuppressWarnings("serial")
public class LoginController extends ResourceController<User>{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String body=getRequestBody(req);
		User usr = JsonService.deserialize(body, User.class);
		setModel(req, usr);
		forward(req, resp, "/model/business/login-dao");
	
	}
}
