package agency.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import agency.dao.ApartmentDao;
import agency.dao.ReservationDao;
import agency.dao.UserDao;
import agency.dto.ReservationDTO;
import agency.model.Reservation;
import agency.model.Status;
import agency.model.User;

@Path("/reservations")
public class ReservationService {
	@Context
	ServletContext context;
	
	@Context
	HttpServletRequest request;
	
	public ReservationService() {}
	
	@PostConstruct
	public void init() {
		System.out.println("reservationinit");
		if (context.getAttribute("reservationDao") == null) {
			ReservationDao reservationDao = new ReservationDao(context.getRealPath(""));
	    	context.setAttribute("reservationDao", reservationDao);
		}
	}


	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReservationDTO> getAllReservations() {
		ReservationDao reservationDao = (ReservationDao) context.getAttribute("reservationDao");
		UserDao userDao = (UserDao) context.getAttribute("userDao");
		User logedUser = (User)request.getSession().getAttribute("loginUser");
		
		return reservationDao.getAllReservationsByRole(logedUser, userDao);
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createReservation(Reservation reservation) {
		ReservationDao reservationDao = (ReservationDao) context.getAttribute("reservationDao");
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		User logedUser = (User)request.getSession().getAttribute("loginUser");
		
		return reservationDao.createReservation(reservation, logedUser, apartmentDao);
	}
	
	@POST
	@Path("/updateStatus/{newStatus}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateStatus(Reservation reservation, @PathParam("newStatus")Status status) {
		ReservationDao reservationDao = (ReservationDao) context.getAttribute("reservationDao");
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		User logedUser = (User)request.getSession().getAttribute("loginUser");
		
		return reservationDao.updateStatus(reservation, status, logedUser, apartmentDao);
	}
	

}
