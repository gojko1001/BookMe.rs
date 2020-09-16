package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import agency.dto.ApartmentDTO;
import agency.model.Amenity;
import agency.model.Apartment;
import agency.model.Role;
import agency.model.User;

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
	
	
	public List<Amenity> getAllAmenities(User user){
		List<Amenity> allAmenities = new ArrayList<Amenity>();
		for(Amenity a : amenities) {
			if(user == null || user.getRole() == Role.Guest || user.getRole() == Role.Host) {
				if(a.isDeleted() == false) {
					allAmenities.add(a);
				}
			} else if(user.getRole() == Role.Administrator) {
				allAmenities.add(a);
			} 
		}
		return allAmenities;
	}
	
	
	public Amenity getAmenity(String id) {
		for(Amenity a : amenities) {
			if(a.getId().equals(id)) {
				return a;
			}
		}
		
		return null;
	}
	
	
	public String addAmenity(Amenity newAmenity, User user) {
		for(Amenity a : amenities) {
			if(a.getId().equals(newAmenity.getId())) {
				return "Sadržaj apartmana nije dodat jer unešen id već postoji.";
			}else if(a.getName().equals(newAmenity.getName())){
				return "Saadržaj apartmana nije dodat jer unešen naziv već posotji.";
			}else if(a.getId().equals("") || a.getName().equals("")) {
				return "Niste popunili sva polja";
			}
		}
			amenities.add(newAmenity);
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			try {
				writer.writeValue(new File(path), getAllAmenities(user));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Greska u radu sa fajlovima");
				e.printStackTrace();
			}
		
		return "Dodat novi sadržaj apartmana";
	}
	
	
	public String updateAmenity(Amenity amenity, User user, ApartmentDao apartmentDao) {
		List<Amenity> allAmenities = getAllAmenities(user);
		if(user == null || user.getRole() == Role.Guest || user.getRole() == Role.Host) {
			return "Samo administrator može menjati sadržaj.";
		} else if(user.getRole() == Role.Administrator) {
			for(Amenity a : allAmenities) {
				if(a.getId().equals(amenity.getId())) {
					a.setName(amenity.getName());
					a.setDeleted(amenity.isDeleted());
					for(Apartment ap : apartmentDao.getApartments()) {
						for(Amenity am : ap.getAmenities()) {
							if(am.getId().equals(amenity.getId())) {
								ap.getAmenities().remove(am);
								break;
							}
						}
					}
				}
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), getAllAmenities(user));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska u radu sa fajlovima");
			e.printStackTrace();
		}
		
		return "Sadržaj je uspešno izmenjen.";
	}
	

	public String deleteAmenityFromApartment(List<ApartmentDTO> allApartments, Amenity amenity) {
		List<Amenity> apartmentAmenities = new ArrayList<Amenity>();
		for(ApartmentDTO ap : allApartments) {
			apartmentAmenities = ap.getAmenities();
			for(Amenity am : apartmentAmenities) {
				if(am.getId().equals(amenity.getId())) {
					// TODO: RESITI
					am.setDeleted(true);
				}
			}
		}
		
		/*ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		
		try {
			writer.writeValue(new File(path), getAllApartments(user));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska u radu sa fajlovima");
			e.printStackTrace();
		}*/
		
		return "Obrisan iz apartmana.";
	}
	
	
}
