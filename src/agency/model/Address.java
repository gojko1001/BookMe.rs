package agency.model;

public class Address {
	private String country;
	private String place;
	private int postalCode;
	private String street;
	private String number;
	
	public Address() {}
	public Address(String country, String place, int postalCode, String street, String number) {
		this.country = country;
		this.place = place;
		this.postalCode = postalCode;
		this.street = street;
		this.number = number;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
