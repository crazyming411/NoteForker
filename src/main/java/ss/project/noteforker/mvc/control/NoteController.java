package ss.project.noteforker.mvc.control;

import java.io.IOException;
//import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.model.domain.Note;
//import ss.project.noteforker.service.json.JsonService;


@SuppressWarnings("serial")
public class NoteController extends ResourceController<Note>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("NoteServlet!");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		
	}
	
}
