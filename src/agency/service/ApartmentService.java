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
import agency.model.Apartment;

@Path("/apartments")
public class ApartmentService {

	@Context
	ServletContext context;
	
	public ApartmentService() {}
	
	@PostConstruct
	public void init() {
		System.out.println("apartmentinit");
		if (context.getAttribute("apartmentDao") == null) {
			ApartmentDao apartmentDao = new ApartmentDao(context.getRealPath(""));
	    	context.setAttribute("apartmentDao", apartmentDao);
		}
	}

	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Apartment> getAllApartments() {
		ApartmentDao apartmentDao = (ApartmentDao) context.getAttribute("apartmentDao");
		
		return apartmentDao.getAllApartments();
	}
}
