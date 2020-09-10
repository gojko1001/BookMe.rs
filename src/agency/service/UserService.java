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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import agency.dao.ApartmentDao;
import agency.dao.ReservationDao;
import agency.dao.UserDao;
import agency.dto.AuthenticationDTO;
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
		if (context.getAttribute("reservationDao") == null) {
			ReservationDao reservationDao = new ReservationDao(context.getRealPath(""));
	    	context.setAttribute("reservationDao", reservationDao);
		}
		if (context.getAttribute("apartmentDao") == null) {
			ApartmentDao apartmentDao = new ApartmentDao(context.getRealPath(""),((ReservationDao)context.getAttribute("reservationDao")).reservations);
	    	context.setAttribute("apartmentDao", apartmentDao);
		}
		if (context.getAttribute("userDao") == null) {
			UserDao userDao = new UserDao(context.getRealPath(""), ((ApartmentDao)context.getAttribute("apartmentDao")).apartments, ((ReservationDao)context.getAttribute("reservationDao")).reservations);
	    	context.setAttribute("userDao", userDao);
		}
	}
	
	
	
	@GET
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User get() {
		User user = (User)request.getSession().getAttribute("loginUser");
		
		return user;
	}

	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		
		return userDao.getAllUsersDTOs();
	}
	
	
	/*
	@GET
	@Path("/allByRole")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsersByRole() {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		User user = (User) request.getSession().getAttribute("loginUser");
		Role role = user.getRole();
		
		return userDao.getAllUsersByRole(role);
	}
	*/
	
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
			return "Pokušajte ponovo. Greška prilikom prijave.";
		}else {
			request.getSession().setAttribute("loginUser", user);
			System.out.println(((User)request.getSession().getAttribute("loginUser")).getUsername());
			
			return "Uspešno ste se ulogovali.";
		}	
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoutUser() {
		User user = (User)request.getSession().getAttribute("loginUser");
		if(user == null) {
			return "Trenutno nema ulogovanog korisnika.";
		}else {
			request.getSession().setAttribute("loginUser", null);
			return "Uspešno izlogovan korisnik.";
		}
	}
	
	
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(User user) {
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		
		return userDao.updateUser(user);
	}
	
	
	
}
