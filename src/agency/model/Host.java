package agency.model;

import java.util.ArrayList;
import java.util.List;

public class Host extends User {
	private List<Apartment> apartmentsForRent = new ArrayList<Apartment>();
	
	public Host() {}
	public Host(String username, String password, String name, String lastName, boolean isMale,
			Role role, List<Apartment> apartmentsForRent) {
		super(username, password, name, lastName, isMale, role);
		this.apartmentsForRent = apartmentsForRent;
	}
	public List<Apartment> getApartmentsForRent() {
		return apartmentsForRent;
	}
	public void setApartmentsForRent(List<Apartment> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}
}
