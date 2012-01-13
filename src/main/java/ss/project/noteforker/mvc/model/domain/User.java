package ss.project.noteforker.mvc.model.domain;

import javax.persistence.Id;

public class User {
	@Id
	private String account;
	
	private String passwd;
	private String index;
	private  Long  maxId=new Long(0);

	public String getAccount(){
		return this.account;
	}
	
	public void setAccount(String account){
			this.account=account;
	}
	
	public String getPasswd(){
		return this.passwd;
	}
	
	public void setPasswd(String passwd){
		this.passwd=passwd;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}
	
}
