package agency.model;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User{
	private List<Apartment> apartmentsRented = new ArrayList<Apartment>();
	private List<Reservation> reservations = new ArrayList<Reservation>();
	
	public Guest() {}
	public Guest(String username, String password, String name, String lastName, boolean male,
			Role role) {
		super(username, password, name, lastName, male, role);
	}
	public List<Apartment> getApartmentsRented() {
		return apartmentsRented;
	}
	public void setApartmentsRented(List<Apartment> apartmentsRented) {
		this.apartmentsRented = apartmentsRented;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
		
}
