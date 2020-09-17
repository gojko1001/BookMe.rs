package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import agency.dto.ApartmentDTO;
import agency.dto.ApartmentFilterDTO;
import agency.dto.AuthenticationDTO;
import agency.dto.GuestDTO;
import agency.dto.HostDTO;
import agency.dto.UserFilterDTO;
import agency.model.Administrator;
import agency.model.Apartment;
import agency.model.Guest;
import agency.model.Host;
import agency.model.Reservation;
import agency.model.Role;
import agency.model.User;

public class UserDao {
	private List<Administrator> admins = new ArrayList<Administrator>();
	private List<Guest> guests = new ArrayList<Guest>();
	private List<Host> hosts = new ArrayList<Host>();
	private String path;
	
	public UserDao(String path, List<Apartment> apartments, List<Reservation> reservations) {
		this.path = path + "json/users.json";
		//System.out.println(this.path);
		loadUsersFromJson();
		mapUsersToReservations(reservations, apartments);
		mapHostsToApartments(apartments);
	}
	
	public User getUserFromUsername(String username) {
		List<User> allUsers = getAllUsers();
		for(User u : allUsers) {
			if(u.getUsername().equals(username)) {
				return u;
			}
		}
		
		return null;
	}
	
	
	public void mapUsersToReservations(List<Reservation> reservations, List<Apartment> apartments) {
		for(Reservation r : reservations) {
			Guest g = (Guest)this.getUserFromUsername(r.getGuest().getUsername());
			g.getReservations().add(r);
			r.setGuest(g);
			
			Apartment a = null;
			
			for(Apartment apart : apartments) {
				if(apart.getId().equals(r.getApartment().getId())) {
					a = apart;
				}
			}
			
			if(!g.getApartmentsRented().contains(a)) {
				g.getApartmentsRented().add(a);
			}
		}
	}
	
	public void mapHostsToApartments(List<Apartment> apartments) {
		for(Apartment a : apartments) {
			Host h = (Host) this.getUserFromUsername(a.getHost().getUsername());
			h.getApartmentsForRent().add(a);
			a.setHost(h);
		}
	}
	
	public List<User> getAllUsersDTOs(){
		List<User> all = new ArrayList<User>();
		all.addAll(admins);
		for(Guest g : guests) {
			all.add(new GuestDTO(g));
		}
		
		for(Host h : hosts) {
			all.add(new HostDTO(h));
		}
		
		return all;
	}
	
	public List<User> getAllUsersByRole(User user){
		if(user == null || user.getRole() == Role.Guest) {
			return null;
		} else if(user.getRole() == Role.Administrator) {
			return getAllUsersDTOs();
		} else {
			List<User> users = new ArrayList<>();
			for(Apartment a : ((Host) user).getApartmentsForRent())
				for(Reservation r : a.getReservations())
					if(!users.contains(r.getGuest()))
						users.add(r.getGuest());
			return users;
		}
	}
	
	public List<User> getAllUsers(){
		List<User> all = new ArrayList<User>();
		all.addAll(admins);
		all.addAll(guests);
		all.addAll(hosts);
		
		return all;
	}
	
	public void loadUsersFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		System.out.println(path);
		File file = new File(path);
			try {
				List<User> users = mapper.readValue(file, new TypeReference<List<User>>() {});
				for(int i=0; i<users.size(); i++) {
					if(users.get(i).getRole() == Role.Administrator) {
						Administrator a = new Administrator(users.get(i).getUsername(), users.get(i).getPassword(), users.get(i).getName(),
								users.get(i).getLastName(), users.get(i).isMale(), Role.Administrator);
						admins.add(a);
					}else if(users.get(i).getRole() == Role.Host) {
						Host h = new Host(users.get(i).getUsername(), users.get(i).getPassword(), users.get(i).getName(),
								users.get(i).getLastName(), users.get(i).isMale(), Role.Host );
						hosts.add(h);
					}else {
						Guest g = new Guest(users.get(i).getUsername(), users.get(i).getPassword(), users.get(i).getName(),
								users.get(i).getLastName(), users.get(i).isMale(), Role.Guest);
						guests.add(g);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	public void addUsersToFile() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer(new DefaultPrettyPrinter());			// da se lepo napise u JSON-u
		ow.writeValue(new File(path), getAllUsersDTOs());
		//ow.writeValue(new File("/json/users.json"), getAllUsersDTOs());
	}
	
	public String addUser(User user) {
		List<User> allUsers = getAllUsers();
		
		for(User u : allUsers) {
			if(u.getUsername().equals(user.getUsername())) {
				return "Korisnik sa prosleđenim korisničkim imenom već postoji.";
			}
		}
		
		if(user.getUsername().equals("") || user.getName().equals("") || user.getLastName().equals("") || user.getPassword().equals("")) {
			return "Niste popunili sva polja.";
		}
		
		Guest guest = new Guest(user.getUsername(), user.getPassword(), user.getName(), user.getLastName(), user.isMale(), user.getRole());
		
		guests.add(guest);
		try {
			addUsersToFile();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("Neuspešno mapiranje.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Greška u radu sa fajlovima.");
			e.printStackTrace();
		}
		
		return "Uspešno ste se registrovali.";
	}
	
	
	public User loginUser(AuthenticationDTO authentication) {
		List<User> allUsers = getAllUsers();
		
		if(!authentication.getUsername().equals("") && !authentication.getPassword().equals("")) {
			for(User u : allUsers) {
				if(u.getUsername().equals(authentication.getUsername()) && u.getPassword().equals(authentication.getPassword())) {
					return u;
				}
			}
		}

		return null;
	}
	
	public User updateUser(User user) {
		List<User> allUsers = getAllUsers();
		for(User u : allUsers) {
			if(u.getUsername().equals(user.getUsername())) {
				u.setLastName(user.getLastName());
				u.setMale(user.isMale());
				u.setName(user.getName());
				u.setPassword(user.getPassword());
				
				try {
					addUsersToFile();
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					System.out.println("Neuspešno mapiranje.");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Greška u radu sa fajlovima.");
					e.printStackTrace();
				}
				
				if(u.getRole() == Role.Administrator) {
					return u;
				}else if (u.getRole() == Role.Host) {
					return new HostDTO((Host) u);
				}else {
					return new GuestDTO((Guest) u);
				}
			}
		}
		return null;
	}
	
	
	public List<User> filterUsers(UserFilterDTO filter, User user){
		List<User> filtered = new ArrayList<>();
			for(User u : getAllUsersByRole(user)) {
				if(!filter.getRole().equals("")) {
					if(filter.getRole().equals("host")) {
						if(u.getRole() != Role.Host) {
							continue;
						}
					}
					if(filter.getRole().equals("guest")) {
						if(u.getRole() != Role.Guest) {
							continue;
						}
					}
					if(filter.getRole().equals("admin")) {
						if(u.getRole() != Role.Administrator) {
							continue;
						}
					}
				}if(!filter.getUsername().equals("")) {
					if(!u.getUsername().contains(filter.getUsername()))
						continue;
				}	
				if(!filter.getSex().equals("")) {
					if(filter.getSex().equals("male")){
						if(u.isMale() == false) {
							continue;
						}
					}else {
						if(u.isMale() == true) {
							continue;
						}
					}
				}
				filtered.add(u);
			}		
		return filtered;	
	}
		
		
	
}
	
