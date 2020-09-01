package agency.model;

public class Comment {
	private Guest guest;
	private Apartment apartment;
	private String text;
	private int rate;
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(Guest guest, Apartment apartment, String text, int rate) {
		super();
		this.guest = guest;
		this.apartment = apartment;
		this.text = text;
		this.rate = rate;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
