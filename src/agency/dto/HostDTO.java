package agency.dto;

import java.util.ArrayList;
import java.util.List;

import agency.model.Apartment;
import agency.model.Host;
import agency.model.User;

public class HostDTO extends User{
	private List<ApartmentDTO> apartmentsForRentDTO = new ArrayList<ApartmentDTO>();
	
	public HostDTO() {}
	
	public HostDTO(Host host) {
		this.setUsername(host.getUsername());
		this.setRole(host.getRole());
		this.setPassword(host.getPassword());
		this.setName(host.getName());
		this.setMale(host.isMale());
		this.setLastName(host.getLastName());
		
		for(Apartment a : host.getApartmentsForRent()) {				// TODO 4: ne nalazi apartmane host-ova
			this.apartmentsForRentDTO.add(new ApartmentDTO(a));
		}
		
	}

	public List<ApartmentDTO> getApartmentsForRentDTO() {
		return apartmentsForRentDTO;
	}

	public void setApartmentsForRentDTO(List<ApartmentDTO> apartmentsForRentDTO) {
		this.apartmentsForRentDTO = apartmentsForRentDTO;
	}
	
	
}
