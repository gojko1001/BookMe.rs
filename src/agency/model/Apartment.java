package agency.model;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
	private String id;
	private Type type;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private List<String> datesForRent = new ArrayList<String>();
	private List<String> freeDates = new ArrayList<String>();
	private Host host;			
	private List<Comment> comments = new ArrayList<Comment>();
	private List<String> photos = new ArrayList<String>();
	private float price;
	private int checkInTime = 14;
	private int checkOutTime = 10;
	private boolean active;
	private List<Amenity> amenities = new ArrayList<Amenity>();
	private List<Reservation> reservations = new ArrayList<Reservation>();
	
	public Apartment() {}

	public Apartment(String id, Type type, int numberOfRooms, int numberOfGuests, Location location,
			List<String> datesForRent, List<String> freeDates, Host host, List<Comment> comments,
			List<String> photos, float price, int checkInTime, int checkOutTime, boolean active,
			List<Amenity> amenities, List<Reservation> reservations) {
		super();
		this.id = id;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.datesForRent = datesForRent;
		this.freeDates = freeDates;
		this.host = host;
		this.comments = comments;
		this.photos = photos;
		this.price = price;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.active = active;
		this.amenities = amenities;
		this.reservations = reservations;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<String> getDatesForRent() {
		return datesForRent;
	}

	public void setDatesForRent(List<String> datesForRent) {
		this.datesForRent = datesForRent;
	}

	public List<String> getFreeDates() {
		return freeDates;
	}

	public void setFreeDates(List<String> freeDates) {
		this.freeDates = freeDates;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime;
	}

	public int getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(int checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public Boolean isValid() {
		if(this.id == null || this.host.getUsername().equals("") ||
				this.numberOfGuests <= 0 || this.numberOfRooms <= 0 ||
				this.location == null)
			return false;
		return true;
	}
	
}
