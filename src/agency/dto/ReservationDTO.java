package agency.dto;

import agency.model.Reservation;
import agency.model.Status;

public class ReservationDTO {
	private String apartmentId;			// na ovaj nacin rezervacija nema apartmentDto, a apartmentDto ima rezervaciju
	private String beginDate;
	private int nights = 1;
	private float totalPrice;
	private String message;
	private String guestUsername;		// ista prica
	private Status status;
	
	public ReservationDTO() {}
	
	public ReservationDTO(Reservation reservation) {
		this.apartmentId = reservation.getApartment().getId();
		this.beginDate =  reservation.getBeginDate();
		this.nights = reservation.getNights();
		this.totalPrice = reservation.getTotalPrice();
		this.message = reservation.getMessage();
		this.guestUsername = reservation.getGuest().getUsername();
		this.status = reservation.getStatus();
	}

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
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

	public String getGuestUsername() {
		return guestUsername;
	}

	public void setGuestUsername(String guestUsername) {
		this.guestUsername = guestUsername;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
	
}
