package ge.edu.freeuni.taxi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Driver implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "name", column = @Column(name = "locationName"))
	})
	private Location location;

	private Date lastWorkingDate;

	private boolean available;

	private Date locationLastUpdateTime;

	public Driver() {}

	public Driver(String name, Location location) {
		this.location = location;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Date getLastWorkingDate() {
		return lastWorkingDate;
	}

	public void setLastWorkingDate(Date lastWorkingDate) {
		this.lastWorkingDate = lastWorkingDate;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setLocationLastUpdateTime(Date locationLastUpdateTime) {
		this.locationLastUpdateTime = locationLastUpdateTime;
	}

	public Date getLocationLastUpdateTime() {
		return locationLastUpdateTime;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Driver) obj).getName().equals(name);
	}

	@Override
	public String toString() {
		return "Driver{" +
				"name=" + name +
				" location=" + location.getName();
	}
}