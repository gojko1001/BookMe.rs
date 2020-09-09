package agency.dto;

import agency.model.Comment;

public class CommentDTO {
	private String guestUsername;
	private String apartmentId;
	private String text;
	private int rate;
	
	public CommentDTO() {}
	
	public CommentDTO(Comment comment) {
		this.guestUsername = comment.getGuest().getUsername();
		this.apartmentId = comment.getApartment().getId();
		this.text = comment.getText();
		this.rate = comment.getRate();
	}
	
	
	public String getGuestUsername() {
		return guestUsername;
	}
	public void setGuestUsername(String guestUsername) {
		this.guestUsername = guestUsername;
	}
	public String getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
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
