package agency.dto;

import java.util.ArrayList;
import java.util.List;

import agency.model.Apartment;
import agency.model.Guest;
import agency.model.Reservation;
import agency.model.User;

public class GuestDTO extends User{
	private List<ApartmentDTO> apartmentsRentedDTO = new ArrayList<ApartmentDTO>();
	private List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
	
	
	public GuestDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public GuestDTO(Guest guest) {
		this.setUsername(guest.getUsername());
		this.setRole(guest.getRole());
		this.setPassword(guest.getPassword());
		this.setName(guest.getName());
		this.setMale(guest.isMale());
		this.setLastName(guest.getLastName());
		
		for(Reservation r : guest.getReservations()) {
			this.reservationsDTO.add(new ReservationDTO(r));
		}
		
		for(Apartment a : guest.getApartmentsRented()) {
			this.apartmentsRentedDTO.add(new ApartmentDTO(a));
		}
	}
	
	public List<ApartmentDTO> getApartmentsRentedDTO() {
		return apartmentsRentedDTO;
	}
	public void setApartmentsRentedDTO(List<ApartmentDTO> apartmentsRentedDTO) {
		this.apartmentsRentedDTO = apartmentsRentedDTO;
	}
	public List<ReservationDTO> getReservationsDTO() {
		return reservationsDTO;
	}
	public void setReservationsDTO(List<ReservationDTO> reservationsDTO) {
		this.reservationsDTO = reservationsDTO;
	}
}
