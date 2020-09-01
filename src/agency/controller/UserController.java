package agency.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/users")
public class UserController {

	@GET
	@Path("/hello")
	public String helloWorld() {
		return "Hi";
	}
	
}
