package agency.model;

public class Administrator extends User{
	public Administrator() {}
	public Administrator(String username, String password, String name, String lastName, boolean isMale, Role role) {
		super(username, password, name, lastName, isMale, role);
	}
}
