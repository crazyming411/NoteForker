package ss.project.noteforker.mvc.model.domain;

import javax.persistence.Id;

public class FileIndex {
	
	@Id
	private Long Id;
	
	private String user;
	
	private String pathInfo;
	private String type;
	
	public FileIndex(){
		this.user=null;
		this.pathInfo=null;
		this.type=null;
	}
	
	public FileIndex(String user, String pathInfo, String type){
		this.user=user;
		this.pathInfo=pathInfo;
		this.type=type;
	}
	
	public Long getId() {
		return Id;
	}
	
	public void setId(Long id) {
		Id = id;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPathInfo() {
		return pathInfo;
	}
	
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
