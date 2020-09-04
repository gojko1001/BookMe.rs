package agency.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import agency.dao.UserDao;
import agency.dto.AuthenticationDTO;
import agency.model.Role;
import agency.model.User;

@Path("/users")
public class UserService {
	
	@Context
	ServletContext context;
	
	@Context
	HttpServletRequest request;
	
	public UserService() {
	}
	
	@PostConstruct
	public void init() {
		//System.out.println("userinit");
		if (context.getAttribute("userDao") == null) {
			UserDao userDao = new UserDao(context.getRealPath(""));
	    	context.setAttribute("userDao", userDao);
		}
	}

	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		
		return userDao.getAllUsers();
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUser(User user) {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		
		return userDao.addUser(user);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(AuthenticationDTO authentication) {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		User user = userDao.loginUser(authentication);
		
		if(user == null) {
			return "Korisnik unetom šifrom i korisničkim imenom ne postoji.";
		}else {
			// TODO 1: da li je vec ulogovan?
			request.getSession().setAttribute("loginUser", user);
			System.out.println(((User)request.getSession().getAttribute("loginUser")).getUsername());
			
			return "Uspešno ulogovan korisnik.";
		}
		
	}
	
	
	
}
