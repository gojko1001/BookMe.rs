package agency.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import agency.dao.ApartmentDao;
import agency.dao.ReservationDao;
import agency.dto.ReservationDTO;
import agency.model.Apartment;
import agency.model.Reservation;

@Path("/reservations")
public class ReservationService {
	@Context
	ServletContext context;
	
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
		
		return reservationDao.getAllReservations();
	}
}
