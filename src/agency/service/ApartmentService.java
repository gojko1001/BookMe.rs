package agency.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import agency.dao.ApartmentDao;
import agency.dao.ReservationDao;
import agency.dto.ApartmentDTO;
import agency.dto.ApartmentFilterDTO;
import agency.model.Apartment;

@Path("/apartments")
public class ApartmentService {

	@Context
	ServletContext context;
	
	public ApartmentService() {}
	
	@PostConstruct
	public void init() {
		//System.out.println("apartmentinit");
		if (context.getAttribute("reservationDao") == null) {
			ReservationDao reservationDao = new ReservationDao(context.getRealPath(""));
	    	context.setAttribute("reservationDao", reservationDao);
		}
		if (context.getAttribute("apartmentDao") == null) {
			ApartmentDao apartmentDao = new ApartmentDao(context.getRealPath(""),((ReservationDao)context.getAttribute("reservationDao")).reservations);
	    	context.setAttribute("apartmentDao", apartmentDao);
		}
	}

	@POST
	@Path("/getApartment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public ApartmentDTO getAppartment(String id) {
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		
		return apartmentDao.getApartmentDTO(id);
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ApartmentDTO> getAllApartments() {
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		
		return apartmentDao.getAllApartments();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean addApartment(Apartment apartment) {
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		
		return apartmentDao.addApartment(apartment);
	}
	
	@POST
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public List<Apartment> applyFilter(ApartmentFilterDTO filter) {
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		
		return apartmentDao.applyFilter(filter);
	}
}
