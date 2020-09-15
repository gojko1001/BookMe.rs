package agency.dao;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import agency.dto.ReservationDTO;
import agency.model.Apartment;
import agency.model.Host;
import agency.model.Reservation;
import agency.model.Role;
import agency.model.User;

public class ReservationDao {
	public List<Reservation> reservations = new ArrayList<Reservation>();
	private String path;
	
	public SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ReservationDao(String path) {
		this.path = path + "json/reservations.json";
		//System.out.println(this.path);
		loadReservationsFromJson();
	}
	
	public void loadReservationsFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		File file = new File(path);
			try {
				this.reservations = mapper.readValue(file,new TypeReference<List<Reservation>>() {});
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	
	public List<ReservationDTO> getAllReservations(){
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		for(Reservation r : reservations)
			reservationsDTO.add(new ReservationDTO(r));
		
		return reservationsDTO;
	}
	
	public List<ReservationDTO> getAllReservationsByRole(User user, UserDao userDao){
		if(user == null)
			return null;
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		if(user.getRole() == Role.Guest) {
			for(ReservationDTO r : getAllReservations())
				if(r.getGuestUsername().equals(user.getUsername()))
					reservationsDTO.add(r);
			return reservationsDTO;
		}else if(user.getRole() == Role.Administrator) {
			return getAllReservations();
		}else {		//Role.Host
			Host h = (Host) userDao.getUserFromUsername(user.getUsername());
			for(ReservationDTO r : getAllReservations())
				for(Apartment a : h.getApartmentsForRent())
					if(r.getApartmentId().equals(a.getId()))
						reservationsDTO.add(r);
			return reservationsDTO;
		}
	}
	
	public String createReservation(Reservation reservation, User user, ApartmentDao apartmentDao) {
		if(!reservation.isObjectValid())
			return "Nisu popunjena sva polja!";
		if(user.getRole() != Role.Guest || user == null)
			return "Samo gost moze napraviti rezervaciju!";
		
		Apartment a = apartmentDao.getApartment(reservation.getApartment().getId());
		
		String dueStr = reservation.getBeginDate();
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dueStr));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		c.add(Calendar.DATE, reservation.getNights());
		dueStr = sdf.format(c.getTime());
 	
		if(!apartmentDao.isDatesInRange(reservation.getBeginDate(), dueStr, a.getFreeDates()))
			return "Zeljeni datum nije na raspolaganju!";
		
		reservations.add(reservation);
		//TODO Azurirati listu freeDates???
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), getAllReservations());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("Neuspesno mapiranje.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greska u radu sa fajlovima");
			e.printStackTrace();
		}
		
		return "Uspesno kreirana rezervacija!";
	}
}
