package agency.dto;

import agency.model.Role;

public class UserFilterDTO {
	private String username;
	private String role;				
	private String sex;			
	
	

	public UserFilterDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public UserFilterDTO(String username, String role, String sex) {
		super();
		this.username = username;
		this.role = role;
		this.sex = sex;
	}

	

	
	
}
