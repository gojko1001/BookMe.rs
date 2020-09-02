package agency.model;

public class User {

	private String username;
	private String password;
	private String name;
	private String lastName;
	private boolean male;
	private Role role;
	
	public User(){}
	
	public User(String username, String password, String name, String lastName, boolean male, Role role) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.male = male;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
