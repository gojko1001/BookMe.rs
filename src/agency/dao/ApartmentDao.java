package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import agency.model.Apartment;
import agency.model.Reservation;
import agency.model.User;

public class ApartmentDao {
	private List<Apartment> apartments = new ArrayList<Apartment>();
	private String path;
	
	public ApartmentDao(String path) {
		this.path = path + "json/apartments.json";
		//System.out.println(this.path);
		loadApartmentsFromJson();
	}
	
	public List<Apartment> getAllApartments(){
		
		return apartments;
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
}
