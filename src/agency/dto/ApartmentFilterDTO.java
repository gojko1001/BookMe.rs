package agency.dto;

import java.time.LocalDate;

public class ApartmentFilterDTO {
	private LocalDate startDate;
	private LocalDate dueDate;
	private String country;
	private String city;
	private float priceFrom;
	private float priceTo;
	private int roomFrom;
	private int roomTo;
	private int spotNum;
	
	public ApartmentFilterDTO(LocalDate startDate, LocalDate dueDate, String country, String city, float priceFrom,
			float priceTo, int roomFrom, int roomTo, int spotNum) {
		super();
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.country = country;
		this.city = city;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.roomFrom = roomFrom;
		this.roomTo = roomTo;
		this.spotNum = spotNum;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public float getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(int priceFrom) {
		this.priceFrom = priceFrom;
	}

	public float getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(int priceTo) {
		this.priceTo = priceTo;
	}

	public int getRoomFrom() {
		return roomFrom;
	}

	public void setRoomFrom(int roomFrom) {
		this.roomFrom = roomFrom;
	}

	public int getRoomTo() {
		return roomTo;
	}

	public void setRoomTo(int roomTo) {
		this.roomTo = roomTo;
	}

	public int getSpotNum() {
		return spotNum;
	}

	public void setSpotNum(int spotNum) {
		this.spotNum = spotNum;
	}
	
	
	
}
