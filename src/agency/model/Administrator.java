package agency.model;

public class Administrator extends User{
	public Administrator() {}
	public Administrator(String username, String password, String name, String lastName, boolean male, Role role) {
		super(username, password, name, lastName, male, role);
	}
}
