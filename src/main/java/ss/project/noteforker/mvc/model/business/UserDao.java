package ss.project.noteforker.mvc.model.business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.ModelAwareServlet;
import ss.project.noteforker.mvc.model.domain.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UserDao extends ModelAwareServlet<User>{
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		User usr=getModel(req);
		Objectify ofy=ObjectifyService.begin();
		if((ofy.query(User.class).filter("account", usr.getAccount()).get())!=null){
			System.out.println("Already");
		}else{
			ObjectifyService.begin().put(usr);
			System.out.println("OK Created");
		}
	}
}
