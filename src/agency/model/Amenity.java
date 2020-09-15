package agency.model;

public class Amenity {
	private String id;
	private String name;
	private boolean deleted=false;
	
	public Amenity() {}
	public Amenity(String id, String name, boolean deleted) {
		this.id = id;
		this.name = name;
		this.deleted = deleted;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
