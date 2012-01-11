package ss.project.noteforker.mvc.control;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.model.domain.User;
import ss.project.noteforker.service.httpretrieve.HttpRetriever;
import ss.project.noteforker.service.json.JsonService;


@SuppressWarnings("serial")
public class UserController extends ResourceController<User>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println(HttpRetriever.getContent("http://dl.dropbox.com/u/15537219/ssfinal.txt"));
		String pathInfo=req.getPathInfo().toLowerCase();
		if(pathInfo.charAt(pathInfo.length()-1) != '/'){
			pathInfo+="/";
		}else;
		
		if(pathInfo.contains("/notes/")){
			forward(req, resp, "/control/note-controller");
		}else if(pathInfo.contains("/user/")){
			forward(req, resp, "/view/405-method-not-allowed");
		}else{
			System.out.println("UserServlet!");
			include(req, resp, "/model/business/user-dao");
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