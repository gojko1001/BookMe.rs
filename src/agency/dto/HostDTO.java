package agency.dto;

import java.util.ArrayList;
import java.util.List;

import agency.model.Apartment;
import agency.model.Host;
import agency.model.User;

public class HostDTO extends User{
	private List<ApartmentDTO> apartmentsForRentDto = new ArrayList<ApartmentDTO>();
	
	public HostDTO() {}
	
	public HostDTO(Host host) {
		this.setUsername(host.getUsername());
		this.setRole(host.getRole());
		this.setPassword(host.getPassword());
		this.setName(host.getName());
		this.setMale(host.isMale());
		this.setLastName(host.getLastName());
		
		for(Apartment a : host.getApartmentsForRent()) {
			this.apartmentsForRentDto.add(new ApartmentDTO(a));
		}
		
	}

	public List<ApartmentDTO> getApartmentsForRentDto() {
		return apartmentsForRentDto;
	}

	public void setApartmentsForRentDto(List<ApartmentDTO> apartmentsForRentDto) {
		this.apartmentsForRentDto = apartmentsForRentDto;
	}
	
	
}
