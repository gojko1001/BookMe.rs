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
import agency.model.Amenity;
import agency.model.Apartment;
import agency.model.Reservation;
import agency.model.Role;
import agency.model.User;

public class ApartmentDao {
	public List<Apartment> apartments = new ArrayList<Apartment>();
	private String path;
	
	public SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ApartmentDao(String path, List<Reservation> reservations, List<Amenity> amenities) {
		this.path = path + "json/apartments.json";
		//System.out.println(this.path);
		loadApartmentsFromJson();
		mapReservationsAndApartments(reservations);
		mapAmenitiesToApartments(amenities);
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
	
	public void mapReservationsAndApartments(List<Reservation> reservations) {		// apartman ima celu rezervaciju, a rezervacija apartman
		for(Reservation r : reservations) {
			Apartment a = this.getApartment(r.getApartment().getId());
			a.getReservations().add(r);
			r.setApartment(a);
		}
	}
	
	public void mapAmenitiesToApartments(List<Amenity> amenities) { 
		for(Apartment a : apartments) {
			List<Amenity> detailedAmenities = new ArrayList<>();
			for(Amenity am : a.getAmenities()) {
				for(Amenity am2 : amenities) {
					if(am.getId().equals(am2.getId())) {
						detailedAmenities.add(am2);
						break;
					}
				}
			}
			a.setAmenities(detailedAmenities);
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
	
	public String addApartment(Apartment apartment) {
		if(apartment.getId().equals(null) || apartment.getType().equals(null) ||apartment.getNumberOfGuests() == 0 || apartment.getNumberOfRooms() == 0 
				|| apartment.getLocation().getAddress().equals(null) || apartment.getLocation().getLatitude() == 0|| apartment.getLocation().getLongitude() == 0 
				|| apartment.getHost().getUsername().equals(null)|| apartment.getPhotos().equals(null) || apartment.getPrice() == 0) {
			return "Niste popunili sva polja";
		} else if(apartment.getNumberOfGuests() <= 0 || apartment.getNumberOfRooms() <= 0 || apartment.getPrice() <= 0) {
			return "Uneti podaci u nevalidnom formatu";
		} 
		
		for(Apartment a : apartments) {
			if(a.getId().equalsIgnoreCase(apartment.getId())) {
				return "Postoji već apartman sa unetim id-om.";
			}	
		}
			
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
		
		return "Apartman je dodat.";
	}

	public List<ApartmentDTO> applyFilter(ApartmentFilterDTO filter, User user){
		List<ApartmentDTO> filtered = new ArrayList<>();

		if(filter.getActivity() == 1) {						// PRIKAZ AKTIVNIH APARTMANA
			for(ApartmentDTO a : getAllApartmentsByRole(user)) {
				if(a.isActive() == true) {
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
					if(filter.getPriceFrom() > -1)
						if(a.getPrice() < filter.getPriceFrom())
							continue;
					if(filter.getPriceTo() > -1)
						if(a.getPrice() > filter.getPriceTo())
							continue;
					if(filter.getRoomFrom() > 0)
						if(a.getNumberOfRooms() < filter.getRoomFrom())
							continue;
					if(filter.getRoomTo() > 0)
						if(a.getNumberOfRooms() > filter.getRoomTo())
							continue;
					if(filter.getSpotNum() > 0)
						if(a.getNumberOfGuests() < filter.getSpotNum())
							continue;
					if(!filter.getStartDate().equals("") || !filter.getDueDate().equals("")) {
						if(!isDatesInRange(filter.getStartDate(), filter.getDueDate(), a.getFreeDates()))
							continue;
					}	
				filtered.add(a);
				}
				
			}
		} else if(filter.getActivity() == 2) {							// PRIKAZ NEAKTIVNIH APARTMANA
			for(ApartmentDTO a : getAllApartmentsByRole(user)) {
				if(a.isActive() == false) {
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
					if(filter.getPriceFrom() > -1)
						if(a.getPrice() < filter.getPriceFrom())
							continue;
					if(filter.getPriceTo() > -1)
						if(a.getPrice() > filter.getPriceTo())
							continue;
					if(filter.getRoomFrom() > 0)
						if(a.getNumberOfRooms() < filter.getRoomFrom())
							continue;
					if(filter.getRoomTo() > 0)
						if(a.getNumberOfRooms() > filter.getRoomTo())
							continue;
					if(filter.getSpotNum() > 0)
						if(a.getNumberOfGuests() < filter.getSpotNum())
							continue;
					if(!filter.getStartDate().equals("") || !filter.getDueDate().equals("")) {
						if(!isDatesInRange(filter.getStartDate(), filter.getDueDate(), a.getFreeDates()))
							continue;
					}	
				filtered.add(a);
				}
			}
		} else {															// PRIKAZ SVIH APARTMANA
			for(ApartmentDTO a : getAllApartmentsByRole(user)) {
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
				if(filter.getPriceFrom() > -1)
					if(a.getPrice() < filter.getPriceFrom())
						continue;
				if(filter.getPriceTo() > -1)
					if(a.getPrice() > filter.getPriceTo())
						continue;
				if(filter.getRoomFrom() > 0)
					if(a.getNumberOfRooms() < filter.getRoomFrom())
						continue;
				if(filter.getRoomTo() > 0)
					if(a.getNumberOfRooms() > filter.getRoomTo())
						continue;
				if(filter.getSpotNum() > 0)
					if(a.getNumberOfGuests() < filter.getSpotNum())
						continue;
				if(!filter.getStartDate().equals("") || !filter.getDueDate().equals("")) {
					if(!isDatesInRange(filter.getStartDate(), filter.getDueDate(), a.getFreeDates()))
						continue;
				}	
			filtered.add(a);
			}
		}
		
		return filtered;
	}
	
	
	public Boolean isDatesInRange(String startStr, String dueStr, List<String> rangeOfDates) {
		Date start = new Date();
		Date due = new Date();
		
		try {
			start = sdf.parse(startStr);
			due = sdf.parse(dueStr);
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
	    
	    for(Date d : datesInRange) {
	    	Boolean isAvailable = false;
	    	for(String s : rangeOfDates) {
	    		try {
					if(sdf.parse(s).compareTo(d) == 0)
						isAvailable = true;
				} catch (ParseException e) {
					e.printStackTrace();
				}
	    	}
	    	if(!isAvailable) {
	    		return false;
	    	}
	    }
	    return true;
	}
}
