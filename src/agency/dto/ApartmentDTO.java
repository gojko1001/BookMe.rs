package agency.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import agency.model.Amenity;
import agency.model.Apartment;
import agency.model.Comment;
import agency.model.Location;
import agency.model.Reservation;
import agency.model.Type;

public class ApartmentDTO {
	private String id;
	private Type type;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private List<LocalDate> datesForRent = new ArrayList<LocalDate>();
	private List<LocalDate> freeDates = new ArrayList<LocalDate>();
	private String hostUsername;
	private List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
	private List<String> photos = new ArrayList<String>();
	private float price;
	private int checkInTime = 14;
	private int checkOutTime = 10;
	private boolean active;
	private List<Amenity> amenities = new ArrayList<Amenity>();
	private List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		
	public ApartmentDTO() {}
	
	public ApartmentDTO(Apartment apartment) {
		this.id = apartment.getId();
		this.type = apartment.getType();
		this.numberOfRooms = apartment.getNumberOfRooms();
		this.numberOfGuests = apartment.getNumberOfGuests();
		this.location = apartment.getLocation();
		this.datesForRent = apartment.getDatesForRent();
		this.freeDates = apartment.getFreeDates();
		this.hostUsername = apartment.getHost().getUsername();
		this.photos = apartment.getPhotos();
		this.price = apartment.getPrice();
		this.checkInTime = apartment.getCheckInTime();
		this.checkOutTime = apartment.getCheckOutTime();
		this.active = apartment.isActive();
		this.amenities = apartment.getAmenities();
		
		for(Comment c : apartment.getComments()) {
			this.commentsDTO.add(new CommentDTO(c));
		}
		
		for(Reservation r : apartment.getReservations()) {
			this.reservationsDTO.add(new ReservationDTO(r));
		}
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
	public List<LocalDate> getDatesForRent() {
		return datesForRent;
	}
	public void setDatesForRent(List<LocalDate> datesForRent) {
		this.datesForRent = datesForRent;
	}
	public List<LocalDate> getFreeDates() {
		return freeDates;
	}
	public void setFreeDates(List<LocalDate> freeDates) {
		this.freeDates = freeDates;
	}
	public String getHostUsername() {
		return hostUsername;
	}
	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}
	public List<CommentDTO> getCommentsDTO() {
		return commentsDTO;
	}
	public void setCommentsDTO(List<CommentDTO> commentsDTO) {
		this.commentsDTO = commentsDTO;
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
	public List<ReservationDTO> getReservationsDTO() {
		return reservationsDTO;
	}
	public void setReservationsDTO(List<ReservationDTO> reservationsDTO) {
		this.reservationsDTO = reservationsDTO;
	}
	
	
	
}
