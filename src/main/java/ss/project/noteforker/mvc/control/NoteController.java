package ss.project.noteforker.mvc.control;

import java.io.IOException;
import java.net.URLDecoder;
//import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.model.domain.Note;
//import ss.project.noteforker.service.json.JsonService;
import ss.project.noteforker.mvc.model.domain.User;
import ss.project.noteforker.service.json.JsonService;


@SuppressWarnings("serial")
public class NoteController extends ResourceController<Note>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		forward(req, resp, "/model/business/note-dao");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String body=URLDecoder.decode(getRequestBody(req), "UTF-8");
		User usr = JsonService.deserialize(body, User.class);
		req.setAttribute("userId", usr.getAccount());
		req.setAttribute("indexPath", usr.getIndex());
		forward(req, resp, "/model/business/note-dao");
		
	}
	
}
