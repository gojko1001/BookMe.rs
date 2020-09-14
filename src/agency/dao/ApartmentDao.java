package agency.dao;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import agency.dto.ApartmentDTO;
import agency.dto.ApartmentFilterDTO;
import agency.model.Apartment;
import agency.model.Reservation;
import agency.model.Role;
import agency.model.User;

public class ApartmentDao {
	public List<Apartment> apartments = new ArrayList<Apartment>();
	private String path;
	
	public SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ApartmentDao(String path, List<Reservation> reservations) {
		this.path = path + "json/apartments.json";
		//System.out.println(this.path);
		loadApartmentsFromJson();
		mapReservationsAndApartments(reservations);
	}
	
	public void mapReservationsAndApartments(List<Reservation> reservations) {		// apartman ima celu rezervaciju, a rezervacija apartman
		for(Reservation r : reservations) {
			Apartment a = this.getApartment(r.getApartment().getId());
			a.getReservations().add(r);
			r.setApartment(a);
		}
	}
		
	
	public Apartment getApartment(String id) {
		for(Apartment a : apartments) {
			if(a.getId().equals(id)) {
				return a;
			}
		}
		
		return null;
	}
	
	public List<ApartmentDTO> getAllApartments(){
		List<ApartmentDTO> apartmentsDto = new ArrayList<ApartmentDTO>();
		for(Apartment a : apartments) {
			apartmentsDto.add(new ApartmentDTO(a));
		}
		
		return apartmentsDto;
	}
	
	
	public List<ApartmentDTO> getAllApartmentsByRole(User user){
		if(user == null || user.getRole() == Role.Guest) {
			List<ApartmentDTO> activeApartments = new ArrayList<>();
			for(ApartmentDTO a : getAllApartments())
				if(a.isActive())
					activeApartments.add(a);
			return activeApartments;
		} else if(user.getRole() == Role.Administrator) {
			return getAllApartments();
		} else {					// Role.Host
			List<ApartmentDTO> ownApartments = new ArrayList<>();
			for(ApartmentDTO a : getAllApartments())
				if(a.getHost().getUsername().equals(user.getUsername()))
					ownApartments.add(a);
			return ownApartments;
		}
	}
	
	public ApartmentDTO getApartmentDTO(String id) {
		ApartmentDTO apartmentDTO = null;
		List<ApartmentDTO> allApartments = getAllApartments();
		for(ApartmentDTO a : allApartments) {
			if(a.getId().equals(id)) {
				apartmentDTO = a;
				return apartmentDTO;
			}
		}
		 
		return null;
	}
	
	
	
	
	public void loadApartmentsFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		File file = new File(path);
			try {
				this.apartments = mapper.readValue(file,new TypeReference<List<Apartment>>() {});
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}

	
	public Boolean addApartment(Apartment apartment) {		
		if(!apartment.isValid())
			return false;
		
		for(Apartment a : apartments)
			if(a.getId().equalsIgnoreCase(apartment.getId()))
				return false;
		
		apartments.add(apartment);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), getAllApartments());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("Neuspesno mapiranje.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska u radu sa fajlovima");
			e.printStackTrace();
		}
		
		return true;
	}

	public List<ApartmentDTO> applyFilter(ApartmentFilterDTO filter){
		List<ApartmentDTO> filtered = new ArrayList<>();
		
		for(Apartment a : apartments) {
			if(!filter.getCountry().equals(""))
				if(!a.getLocation()
						.getAddress()
						.getCountry()
						.contains(filter.getCountry()))
					continue;
			if(!filter.getCity().equals(""))
				if(!a.getLocation()
						.getAddress()
						.getPlace()
						.contains(filter.getCity()))
					continue;
			if(filter.getPriceFrom() != -1)
				if(a.getPrice() < filter.getPriceFrom())
					continue;
			if(filter.getPriceTo() != -1)
				if(a.getPrice() > filter.getPriceTo())
					continue;
			if(filter.getRoomFrom() != -1)
				if(a.getNumberOfRooms() < filter.getRoomFrom())
					continue;
			if(filter.getRoomTo() != -1)
				if(a.getNumberOfRooms() > filter.getRoomTo())
					continue;
			if(filter.getSpotNum() != -1)
				if(a.getNumberOfGuests() < filter.getSpotNum())
					continue;
			if(!filter.getStartDate().equals("") || !filter.getDueDate().equals("")) {
				Date start = new Date();
				Date due = new Date();
				
				try {
					start = sdf.parse(filter.getStartDate());
					due = sdf.parse(filter.getDueDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				List<Date> datesInRange = new ArrayList<>();
			    Calendar calendar = new GregorianCalendar();
			    calendar.setTime(start);
			    
			    Calendar endCalendar = new GregorianCalendar();
			    endCalendar.setTime(due);
			 
			    while (calendar.before(endCalendar)) {
			        Date result = calendar.getTime();
			        System.out.println(result);
			        datesInRange.add(result);
			        calendar.add(Calendar.DATE, 1);
			    }
			    
			    Boolean fullAvailable = true;		// Flag za ceo set datuma
			    for(Date d : datesInRange) {
			    	Boolean isAvailable = false;	// Pojedinacni flag
			    	for(String s : a.getFreeDates()) {
			    		try {
							if(sdf.parse(s).compareTo(d) == 0)
								isAvailable = true;
						} catch (ParseException e) {
							e.printStackTrace();
						}
			    	}
			    	if(!isAvailable) {
			    		break;
			    	}
			    }
			    if(!fullAvailable)
			    	continue;
			}	
			filtered.add(new ApartmentDTO(a));
		}
		return filtered;
	}
}
