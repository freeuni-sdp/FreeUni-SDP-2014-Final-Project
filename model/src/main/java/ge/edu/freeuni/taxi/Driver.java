package ge.edu.freeuni.taxi;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class Driver implements Serializable{
	
	private long id;
	private String driversID;
	
	public Driver() {}
	
	@OneToMany
	private List<Location> locations;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void addLocation(Location location) {
		locations.add(location);
	}

	public String getDriversID() {
		return driversID;
	}

	public void setDriversID(String driversID) {
		this.driversID = driversID;
	}
}
