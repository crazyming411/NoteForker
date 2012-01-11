package ss.project.noteforker.mvc.model.domain;

import javax.persistence.Id;

public class Note {
	
	@Id
	private Long id;
	
	private String user;
	private String path;
	
	private String  title;
	private String  content;
	private boolean privacy;
	
	public Note(){
		this.user=null;
		this.path=null;
		this.title=null;
		this.content=null;
		this.privacy=false;
	}
	
	public Note(String user, String path, String title, String content){
		this.user=user;
		this.path=path;
		this.title=title;
		this.content=content;
		this.privacy=false;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	
}
