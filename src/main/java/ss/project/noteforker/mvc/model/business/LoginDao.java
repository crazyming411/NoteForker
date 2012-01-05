package ss.project.noteforker.mvc.model.business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.ModelAwareServlet;
import ss.project.noteforker.mvc.model.domain.User;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class LoginDao extends ModelAwareServlet<User>{
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		User usr=getModel(req);
		User correctUsr=ObjectifyService.begin().query(User.class).filter("account", usr.getAccount()).get();
		if(correctUsr==null){
			System.out.println("No Such Account!");
		}else if((usr.getPasswd()).equals(correctUsr.getPasswd())){
			System.out.println("OK");
		}else{
			System.out.println("Wrong Password!");
		}
	}
}
