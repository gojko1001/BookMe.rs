package agency.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import agency.dao.UserDao;
import agency.model.User;

@Path("/users")
public class UserService {
	
	@Context
	ServletContext context;
	
	public UserService() {
	}
	
	@PostConstruct
	public void init() {
		System.out.println("userinit");
		if (context.getAttribute("userDao") == null) {
			UserDao userDao = new UserDao(context.getRealPath(""));
	    	context.setAttribute("userDao", userDao);
		}
	}
	
	@GET
	@Path("/hello")
	public String helloWorld() {
		return "Hi";
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		
		return userDao.getAllUsers();
	}
	
	
	
	
}
