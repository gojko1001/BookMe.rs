package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import agency.dto.ApartmentDTO;
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
	
	public List<ApartmentDTO> getAllApartments(){
		List<ApartmentDTO> apartmentsDto = new ArrayList<ApartmentDTO>();
		for(Apartment a : apartments) {
			apartmentsDto.add(new ApartmentDTO(a));
		}
		
		return apartmentsDto;
	}
	
	public Apartment getApartment(String id) {
		for(Apartment a : apartments) {
			if(a.getId().equals(id)) {
				return a;
			}
		}
		
		return null;
	}
	
	public void loadApartmentsFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		File file = new File(path);
			try {
				this.apartments = mapper.readValue(file,new TypeReference<List<Apartment>>() {});
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	
	
	
	
	
}
