package ss.project.noteforker.mvc.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ss.project.noteforker.mvc.model.domain.Note;


@SuppressWarnings("serial")
public class SearchController extends ResourceController<Note>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		include(req, resp, "/model/business/search-dao");
	}
	
}
