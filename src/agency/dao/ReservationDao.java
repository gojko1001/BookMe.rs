package agency.dao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import agency.model.Status;
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
		
		String dueStr = apartmentDao.getDueDate(reservation.getBeginDate(), reservation.getNights());
 	
		if(!apartmentDao.isDatesInRange(reservation.getBeginDate(), dueStr, a.getFreeDates()))
			return "Zeljeni datum nije na raspolaganju!";
		
		reservations.add(reservation);
		apartmentDao.mapReservationsAndApartments(reservations);
		apartmentDao.updateFreeDates(a.getId());
		
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
	// TODO: Provera
	public void updateStatus(ReservationDTO reservation, Status status, User user, ApartmentDao apartmentDao) {
		if(user == null)
			return;
		if(user.getRole() == Role.Guest && status != Status.withdrawal)
			return;
		
		if(user.getRole() == Role.Host && (status != Status.ended || status != Status.denied || status != Status.accepted) )
			return;
		
		if(user.getRole() == Role.Guest || user.getRole() == Role.Host)
			for(ReservationDTO r : getAllReservations())
				if(r.getApartmentId().equals(reservation.getApartmentId()) && 
						r.getBeginDate().equals(reservation.getBeginDate()) && 
						(r.getStatus() == Status.accepted || r.getStatus() == Status.created)) {
					r.setStatus(status);
					
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
					
					if(status == Status.denied || status == Status.withdrawal)			// Ako se odbije ili otkaze rezervacija, oslobadjamo datume
						apartmentDao.updateFreeDates(reservation.getApartmentId());
					return;
				}
	}
	
	
}
