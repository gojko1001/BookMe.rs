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

import agency.model.Amenity;
import agency.model.Apartment;

public class AmenityDao {
	public List<Amenity> amenities = new ArrayList<Amenity>();
	private String path;
	
	public AmenityDao(String path) {
		this.path = path + "json/amenities.json";
		loadAmenitiesFromJson();
	}
	
	
	public void loadAmenitiesFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		File file = new File(path);
			try {
				this.amenities = mapper.readValue(file,new TypeReference<List<Amenity>>() {});
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	
	
	public List<Amenity> getAllAmenities(){
		List<Amenity> allAmenities = new ArrayList<Amenity>();
		for(Amenity a : amenities) {
			allAmenities.add(a);
			//System.out.println(a.getId() + ", ");
		}
		
		return allAmenities;
	}
	
	public String addAmenity(Amenity newAmenity) {
		for(Amenity a : amenities) {
			if(a.getId().equals(newAmenity.getId())) {
				return "Sadržaj apartmana nije dodat jer unešen id već postoji.";
			}else if(a.getName().equals(newAmenity.getName())){
				return "Saadržaj apartmana nije dodat jer unešen naziv već posotji.";
			}
		}
			amenities.add(newAmenity);
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			try {
				writer.writeValue(new File(path), getAllAmenities());
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Greska u radu sa fajlovima");
				e.printStackTrace();
			}
		
		return "Dodat novi sadržaj apartmana";
	}
	
	
	public String removeAmenity(Amenity amenity) {
		amenities.remove(amenity);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), getAllAmenities());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska u radu sa fajlovima");
			e.printStackTrace();
		}
		
		return "Obrisan";
	}
	
	
}
