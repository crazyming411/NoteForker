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
		
		StringBuffer pathInfo=new StringBuffer(req.getPathInfo().toLowerCase());
		if(pathInfo.charAt(pathInfo.length()-1) != '/'){
			pathInfo.append('/');
		}else;
		
		if(pathInfo.toString().contains("/notes/")){	//get note!
			pathInfo.deleteCharAt(pathInfo.length()-1);
			pathInfo.deleteCharAt(0);
			
			String userId=pathInfo.toString().substring(0, pathInfo.toString().indexOf('/'));
			String noteId=pathInfo.toString().substring(pathInfo.toString().lastIndexOf('/'));
			
			noteId=noteId.replaceAll("/", "");
			
			req.setAttribute("noteId", noteId);
			req.setAttribute("userId", userId);
			
			forward(req, resp, "/control/note-controller");
		}else if(pathInfo.toString().contains("/user/")){	//PRIVATE URL use for login, create account
			forward(req, resp, "/view/405-method-not-allowed");
		}else if(pathInfo.toString().contains("favicon.ico")){	//
			return;
		}else{
			System.out.println("UserServlet!");
			req.setAttribute("userId", req.getPathInfo().substring(1).toLowerCase());
			forward(req, resp, "/model/business/user-dao");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String mode=req.getHeader("usage");
		if(!mode.equals("index")){
			String body=URLDecoder.decode(getRequestBody(req), "UTF-8");
			User usr = JsonService.deserialize(body, User.class);
			setModel(req, usr);
			
			forward(req, resp, "/model/business/user-dao");
			//forward(req, resp, "/view/user-json-view");
		}else{
			forward(req, resp, "/control/note-controller");
		}
	}
	
}