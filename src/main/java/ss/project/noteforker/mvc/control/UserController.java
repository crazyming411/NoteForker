package ss.project.noteforker.mvc.control;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.model.domain.User;
import ss.project.noteforker.service.json.JsonService;


@SuppressWarnings("serial")
public class UserController extends ResourceController<User>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		include(req, resp, "/model/business/user-dao");
		if(req.getHeader("Accept").contains("text/html")){
			System.out.println("HTML");			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		String body=URLDecoder.decode(getRequestBody(req), "UTF-8");
		User usr = JsonService.deserialize(body, User.class);
		setModel(req, usr);
		
		include(req, resp, "/model/business/user-dao");
		
		forward(req, resp, "/view/user-json-view");
	}
	
}