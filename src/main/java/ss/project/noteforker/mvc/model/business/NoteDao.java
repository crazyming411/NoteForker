package ss.project.noteforker.mvc.model.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ss.project.noteforker.mvc.ModelAwareServlet;
import ss.project.noteforker.mvc.model.domain.FileIndex;
import ss.project.noteforker.mvc.model.domain.Note;
import ss.project.noteforker.mvc.model.domain.User;
import ss.project.noteforker.service.httpretrieve.HttpRetriever;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class NoteDao extends ModelAwareServlet<Note>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String id= (String) req.getAttribute("noteId");
		String usr=(String) req.getAttribute("userId");
		
		Long noteId=Long.parseLong(id);
		
		Note note=ObjectifyService.begin().query(Note.class).filter("user", usr).filter("docId", noteId).get();
		if(note!=null && !note.isPrivacy()){
			//TODO - Response
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(note.getContent());
		}else{
			//TODO - Response
			resp.setStatus(404);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session=req.getSession(false);
		String userId=(String) req.getAttribute("userId");
		if(session.getAttribute("login").equals(userId)){
			Objectify ofy=ObjectifyService.begin();
			String indexPath=(String) req.getAttribute("indexPath");
			User usr=ofy.get(User.class, userId);
			usr.setIndex(indexPath);
			int maxId=usr.getMaxId().intValue();
			
			HashMap<String, String> data=IndexParser.parseContent(HttpRetriever.getContent(indexPath));
						
			indexPath= indexPath.substring(0, indexPath.lastIndexOf('/'));
			StringBuffer buf= new StringBuffer();
			buf.append(indexPath);
			
			ArrayList<Note> current=new ArrayList<Note>();
			ArrayList<Note> newNote=new ArrayList<Note>();
			ArrayList<FileIndex> fIndex=new ArrayList<FileIndex>();
			
			for(Map.Entry<String, String> entry : data.entrySet()){
				if(entry.getValue().equals("File")){
					
					String content=HttpRetriever.getContent(buf.append(entry.getKey()).toString());
					StringBuffer title=new StringBuffer(content.substring(0, content.indexOf('\n')));
					title.delete(0, 2);
					content=content.substring(content.indexOf('\n')+1);
					
					Note currentNote=ofy.query(Note.class).filter("user", userId).filter("path", entry.getKey()).get();
					if(currentNote!=null){
						ofy.delete(Note.class, currentNote.getId());
						currentNote.setTitle(title.toString());
						currentNote.setContent(content);
						current.add(currentNote);
						//ofy.put(currentNote);
					}else{
						Note note=new Note(userId, entry.getKey(), title.toString(), content, new Long(maxId));
						newNote.add(note);
						//ofy.put(note);
						maxId++;
					}
				}
				FileIndex fileIndex=new FileIndex(userId, entry.getKey(), entry.getValue());
				fIndex.add(fileIndex);
				//ofy.put(fileIndex);
				buf.delete(indexPath.length(), buf.length());
			}
			ofy.put(current);
			ofy.put(newNote);
			ofy.put(fIndex);
			
			usr.setMaxId(new Long(maxId));
			ofy.put(usr);
			
			List<Note> qq=ofy.query(Note.class).filter("user", userId).list();
			for(Note n : qq){
				System.out.println(n.getId() + " : " + n.getDocId()+" : "+ n.getTitle());
			}
			//TODO - Response
		}
	}
}
