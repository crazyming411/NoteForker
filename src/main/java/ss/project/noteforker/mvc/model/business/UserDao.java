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
		
		String id=req.getPathInfo().substring(1).toLowerCase();
		System.out.println(id);
		HttpSession session=req.getSession(false);
		
		if(session!=null && session.getAttribute("login").equals(id)){
			System.out.println("Get Personal Page! "+id);
		}else{
			System.out.println("Get Public Page! "+id);
		}
			
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String mode=req.getHeader("usage");
		User usr=getModel(req);
		usr.setAccount(usr.getAccount().toLowerCase());
		Objectify ofy=ObjectifyService.begin();
		
		if(mode.equals("create")){
			
			if((ofy.query(User.class).filter("account", usr.getAccount()).get())!=null){
				System.out.println("Already");
			}else{
				ObjectifyService.begin().put(usr);
				System.out.println("OK Created");
				HttpSession session=req.getSession(true);
				session.setAttribute("login", usr.getAccount());
			}
			
		}else if(mode.equals("login")){
			
			User correctUsr=ObjectifyService.begin().query(User.class).filter("account", usr.getAccount()).get();
			if(correctUsr==null){
				System.out.println("No Such Account!");
			}else if((usr.getPasswd()).equals(correctUsr.getPasswd())){
				HttpSession session=req.getSession(true);
				if(session.isNew()){
					session.setAttribute("login", usr.getAccount());
					System.out.println("New Login!");
					Cookie cookie=new Cookie("user",usr.getAccount());
					resp.addCookie(cookie);
				}else{
					System.out.println("Welcome Back!");
					session.setAttribute("login", usr.getAccount());
				}
			}else{
				System.out.println("Wrong Password!");
			}
		}else;
		
	}
	
}
