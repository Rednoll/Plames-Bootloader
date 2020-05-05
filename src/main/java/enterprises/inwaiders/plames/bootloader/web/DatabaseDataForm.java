package enterprises.inwaiders.plames.bootloader.web;

public class DatabaseDataForm {
	
	public String username;
	public String password;
	public String url;
	public String type;
	
	public void setType(String type) {
		
		this.type = type;
	}
	
	public void setUrl(String url) {
		
		this.url = url;
	}
	
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	public void setUsername(String username) {
		
		this.username = username;
	}
}