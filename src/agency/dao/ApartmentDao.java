package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class ApartmentDao {
	public List<Apartment> apartments = new ArrayList<Apartment>();
	private String path;
	
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

	
	// TODO: datumi ne rade! + ponovnim pokretanjem servera ne radi!
	public Boolean addApartment(Apartment apartment) {		
		if(!apartment.isValid())
			return false;
		
		for(Apartment a : apartments)
			if(a.getId().equalsIgnoreCase(apartment.getId()))
				return false;
		
		apartments.add(apartment);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
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

	public List<Apartment> applyFilter(ApartmentFilterDTO filter){
		List<Apartment> filtered = new ArrayList<>();
		
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
				if(a.getNumberOfGuests() != filter.getSpotNum())
					continue;
			//TODO: Filter za datum + filteri po rolama
			filtered.add(a);
		}
		return filtered;
	}
	
	

	
}
