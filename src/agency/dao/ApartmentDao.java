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
import agency.model.Status;
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
		List<ApartmentDTO> allApartments = new ArrayList<ApartmentDTO>();
		for(Apartment a : apartments) {
			allApartments.add(new ApartmentDTO(a));
		}
		
		return allApartments;
	}
	
	public List<Apartment> getApartments(){
		return this.apartments;
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
	
	public String addApartment(Apartment apartment, AmenityDao amenityDao) {
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
		
		for(Amenity am : apartment.getAmenities()) {
			am = amenityDao.getAmenity(am.getId());
		}
		
		List<String> dates = new ArrayList<>();
		try {
			for(Date d : this.getDatesInRange(sdf.parse(apartment.getDatesForRent().get(0)), sdf.parse(apartment.getDatesForRent().get(1))))
				dates.add(sdf.format(d));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		apartment.setDatesForRent(dates);
		apartment.setFreeDates(dates);
			
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
	
	
	public String deleteApartment(String id, User user, ReservationDao reservationDao) {
		if(user == null || user.getRole() == Role.Guest) {
			return "Samo administrator i domaćin mogu brisati apartman.";
		} else if(user.getRole() == Role.Administrator || user.getRole() == Role.Host) {
			for(Apartment a : apartments) {
				if(a.getId().equals(id)) {
					a.setView(false);			//"obrisan" iz apartmana
					for(Reservation r : reservationDao.getReservations()) {
						if(r.getApartment().getId().equals(id)) {
							reservationDao.reservations.remove(r);
							break;
						}
					}
				}
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), getAllApartments());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska u radu sa fajlovima");
			e.printStackTrace();
		}
		
		return "Sadržaj je uspešno izmenjen.";
	}
	
	
	public String updateApartment(Apartment apartment) {
		for(Apartment a : apartments) {
			if(a.getId().equals(apartment.getId())) {
				a.setType(apartment.getType());
				a.setNumberOfRooms(apartment.getNumberOfRooms());
				a.setNumberOfGuests(apartment.getNumberOfGuests());
				a.setLocation(apartment.getLocation());
				//a.setPhotos(apartment.getPhotos());
				a.setPrice(apartment.getPrice());
				a.setCheckInTime(apartment.getCheckInTime());
				a.setCheckOutTime(apartment.getCheckOutTime());
				a.setAmenities(apartment.getAmenities());
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
				try {
					writer.writeValue(new File(path), getAllApartments());
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Greska u radu sa fajlovima");
					e.printStackTrace();
				}
			
				return "Apartman je izmenjen.";
			}
		}
		return "Apartman nije izmenjen";
	}
	
	public void updateFreeDates(String id) {
		for(Apartment a : apartments) {
			if(id.equals(a.getId())) {
				List<String> takenDates = new ArrayList<>();
				for(String s : a.getDatesForRent()) {
					for(Reservation r : a.getReservations()) {
						if(r.getStatus() == Status.created || r.getStatus() == Status.accepted) {
							String dueStr = getDueDate(r.getBeginDate(), r.getNights());
							try {
								for(Date d : getDatesInRange(sdf.parse(r.getBeginDate()), sdf.parse(dueStr))) {
									if(d.compareTo(sdf.parse(s)) == 0) 
										takenDates.add(s);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
				List<String> newFreeDates = a.getDatesForRent();
				for(String s : takenDates)
					newFreeDates.remove(s);
				a.setFreeDates(newFreeDates);
				
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

				return;
			}
		}
	}
	

	public List<ApartmentDTO> applyFilter(ApartmentFilterDTO filter, User user){
		List<ApartmentDTO> filtered = new ArrayList<>();

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
					switch(filter.getActivity()) {
					case 1:
						if(!a.isActive())
							continue;
						break;
					case 2:
						if(a.isActive())
							continue;
						break;
					}
					
				filtered.add(a);	
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
	    
	    for(Date d : getDatesInRange(start, due)) {
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
	
	public List<Date> getDatesInRange(Date start, Date due){
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
	    return datesInRange;
	}
	
	public String getDueDate(String startDate, int daysToAdd) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(startDate));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		c.add(Calendar.DATE, daysToAdd);
		return sdf.format(c.getTime());
	}
	
	
	
	
	
	
}
