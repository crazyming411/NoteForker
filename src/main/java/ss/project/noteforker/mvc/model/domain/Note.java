package ss.project.noteforker.mvc.model.domain;

import javax.persistence.Id;

public class Note {
	
	@Id
	private Long id;
	
	private String user;
	private String path;
	private  Long  docId;

	private String  title;
	private String  content;
	private boolean privacy; //true if it's a private note
	
	public Note(){
		this.user=null;
		this.path=null;
		this.title=null;
		this.content=null;
		this.privacy=true;
		this.docId=new Long(-1);
	}
	
	public Note(String user, String path, String title, String content, Long docId){
		this.user=user;
		this.path=path;
		this.title=title;
		this.content=content;
		this.privacy=false;
		this.docId=docId;
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
	
	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}
}
