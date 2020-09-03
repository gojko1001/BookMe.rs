package agency.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import agency.model.Administrator;
import agency.model.Guest;
import agency.model.Host;
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
	
}
