package ss.project.noteforker.mvc.model.business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ss.project.noteforker.mvc.ModelAwareServlet;
import ss.project.noteforker.mvc.model.domain.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UserDao extends ModelAwareServlet<User>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String id=(String) req.getAttribute("userId");
		System.out.println(id);
		User usr=ObjectifyService.begin().query(User.class).filter("account", id).get();
		HttpSession session=req.getSession(false);
		if(usr!=null){
			if(session!=null && session.getAttribute("login").equals(id)){
				//TODO - Response
				System.out.println("Get Personal Page! "+id);
			}else{
				//TODO - Response
				System.out.println("Get Public Page! "+id);
				
			}
		}else{
			System.out.println("Found Nothing!");
			resp.setStatus(404);
		}
			
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String mode=req.getHeader("usage");
		User usr=getModel(req);
		usr.setAccount(usr.getAccount().toLowerCase());
		resp.setHeader("Cache-Control", "no-cache");
		//resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("UTF-8");
		Objectify ofy=ObjectifyService.begin();
		
		if(mode.equals("create")){
			
			if((ofy.query(User.class).filter("account", usr.getAccount()).get())!=null){
				System.out.println("Already");
				resp.setStatus(400);
			}else{
				ObjectifyService.begin().put(usr);
				System.out.println("OK Created");
				HttpSession session=req.getSession(true);
				session.setAttribute("login", usr.getAccount());
				
				resp.setStatus(201);
				Cookie cookie=new Cookie("user",usr.getAccount());
				cookie.setPath("/");
				cookie.setMaxAge(24*60*60*14);
				resp.addCookie(cookie);
			}
			
		}else if(mode.equals("login")){
			
			User correctUsr=ObjectifyService.begin().query(User.class).filter("account", usr.getAccount()).get();
			if(correctUsr==null){
				//TODO - Response
				resp.setStatus(404);
				System.out.println("No Such Account!");
			}else if((usr.getPasswd()).equals(correctUsr.getPasswd())){
				HttpSession session=req.getSession(true);
				if(session.isNew()){
					//TODO - Response
					session.setAttribute("login", usr.getAccount());
					System.out.println("New Login!");
					resp.setStatus(200);
					Cookie cookie=new Cookie("user",usr.getAccount());
					cookie.setPath("/");
					cookie.setMaxAge(24*60*60*14);
					resp.addCookie(cookie);
					
				}else{
					//TODO - Response
					System.out.println("Welcome Back!");
					session.setAttribute("login", usr.getAccount());
					resp.setStatus(200);
				}
			}else{
				//TODO - Response
				System.out.println("Wrong Password!");
				resp.setStatus(400);
			}
		}else if(mode.equals("logout")){
			HttpSession session=req.getSession(true);
			session.invalidate();
			Cookie cookie=new Cookie("user", "");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			resp.setStatus(200);
		}else;
		
	}
	
}
