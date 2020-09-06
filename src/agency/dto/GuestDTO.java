package agency.dto;

import java.util.ArrayList;
import java.util.List;

import agency.model.Apartment;
import agency.model.Guest;
import agency.model.Reservation;
import agency.model.User;

public class GuestDTO extends User{
	private List<ApartmentDTO> apartmentsRentedDto = new ArrayList<ApartmentDTO>();
	private List<ReservationDTO> reservationsDto = new ArrayList<ReservationDTO>();
	
	
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
			this.reservationsDto.add(new ReservationDTO(r));
		}
		
		for(Apartment a : guest.getApartmentsRented()) {
			this.apartmentsRentedDto.add(new ApartmentDTO(a));
		}
	}
	
	public List<ApartmentDTO> getApartmentsRentedDto() {
		return apartmentsRentedDto;
	}
	public void setApartmentsRentedDto(List<ApartmentDTO> apartmentsRentedDto) {
		this.apartmentsRentedDto = apartmentsRentedDto;
	}
	public List<ReservationDTO> getReservationsDto() {
		return reservationsDto;
	}
	public void setReservationsDto(List<ReservationDTO> reservationsDto) {
		this.reservationsDto = reservationsDto;
	}
}
