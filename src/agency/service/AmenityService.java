package agency.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import agency.dao.AmenityDao;
import agency.model.Amenity;

@Path("/amenities")
public class AmenityService {
	
	@Context
	ServletContext context;
	
	@Context
	HttpServletRequest request;
	
	public AmenityService() {
	}
	
	@PostConstruct
	public void init() {
		//System.out.println("amenityinit");
		//if (context.getAttribute("amenityDao") == null) {
			AmenityDao amenityDao = new AmenityDao(context.getRealPath(""));
	    	context.setAttribute("amenityDao", amenityDao);
		//}
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Amenity> getAllAmenities() {
		AmenityDao amenityDao = (AmenityDao) context.getAttribute("amenityDao");
		
		return amenityDao.getAllAmenities();
	}
	
	
	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addAmenity(Amenity amenity) {
		AmenityDao amenityDao = (AmenityDao) context.getAttribute("amenityDao");
		
		return amenityDao.addAmenity(amenity);
	}
	
	
	
	
	
}
