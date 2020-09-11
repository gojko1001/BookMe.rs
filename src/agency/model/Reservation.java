package agency.model;

import java.time.LocalDate;

public class Reservation {
	private Apartment apartment;
	private String beginDate;
	private int nights = 1;
	private float totalPrice;
	private String message;
	private Guest guest;
	private Status status;
	
	public Reservation() {}

	public Reservation(Apartment apartment, String beginDate, int nights, float totalPrice, String message,
			Guest guest, Status status) {
		super();
		this.apartment = apartment;
		this.beginDate = beginDate;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.message = message;
		this.guest = guest;
		this.status = status;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
