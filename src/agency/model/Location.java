package agency.model;

public class Location {
	private float latitude;		//geog. sirina
	private float longitude;
	private Address address;
	
	public Location() {}
	public Location(float latitude, float longitude, Address address) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
