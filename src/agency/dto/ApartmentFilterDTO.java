package agency.dto;


public class ApartmentFilterDTO {
	private int activity;			//0 - svi; 1 - aktivni; 2 - neaktivni
	private String startDate;
	private String dueDate;
	private String country;
	private String city;
	private float priceFrom;
	private float priceTo;
	private int roomFrom;
	private int roomTo;
	private int spotNum;
	
	public ApartmentFilterDTO() {}
	
	public ApartmentFilterDTO(int activity, String startDate, String dueDate, String country, String city, float priceFrom,
			float priceTo, int roomFrom, int roomTo, int spotNum) {
		super();
		this.setActivity(activity);
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
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

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}
	
	
	
}
