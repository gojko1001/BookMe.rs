package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import agency.dto.AuthenticationDTO;
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
	//private List<User> users = new ArrayList<User>();
	private String path;
	
	public UserDao(String path) {
		this.path = path + "json/users.json";
		//System.out.println(this.path);
		loadUsersFromJson();
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
	
	// TODO 2: ne pamti zauvek..
	public void addUsersToFile() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer(new DefaultPrettyPrinter());			// da se lepo napise u JSON-u
		ow.writeValue(new File(path), getAllUsers());
		//ow.writeValue(new File("/json/users.json"), getAllUsers());
	}
	
	public String addUser(User user) {
		List<User> allUsers = getAllUsers();
		for(User u : allUsers) {
			if(u.getUsername().equals(user.getUsername())) {
				return "Korisnik sa prosledjenim korisničkim imenom već postoji.";
			}
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
		
		return "Uspešna registracija";
	}
	
	
	public User loginUser(AuthenticationDTO authentication) {
		List<User> allUsers = getAllUsers();
		for(User u : allUsers) {
			if(u.getUsername().equals(authentication.getUsername()) && u.getPassword().equals(authentication.getPassword())) {
				return u;
			}
		}
		
		return null;
	}

	
}
