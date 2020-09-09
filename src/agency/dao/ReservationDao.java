package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import agency.dto.ReservationDTO;
import agency.model.Reservation;

public class ReservationDao {
	public List<Reservation> reservations = new ArrayList<Reservation>();
	private String path;
	
	public ReservationDao(String path) {
		this.path = path + "json/reservations.json";
		//System.out.println(this.path);
		loadReservationsFromJson();
	}
	
	public List<ReservationDTO> getAllReservations(){
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		for(Reservation r : reservations) {
			reservationsDTO.add(new ReservationDTO(r));
		}
		
		return reservationsDTO;
	}
	
	public void loadReservationsFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		File file = new File(path);
			try {
				this.reservations = mapper.readValue(file,new TypeReference<List<Reservation>>() {});
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
