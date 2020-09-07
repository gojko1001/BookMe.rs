package agency.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Host extends User {
	@JsonIgnore
	private List<Apartment> apartmentsForRent = new ArrayList<Apartment>();
	
	public Host() {}
	public Host(String username, String password, String name, String lastName, boolean male,
			Role role) {
		super(username, password, name, lastName, male, role);
	}
	public List<Apartment> getApartmentsForRent() {
		return apartmentsForRent;
	}
	public void setApartmentsForRent(List<Apartment> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}
}
