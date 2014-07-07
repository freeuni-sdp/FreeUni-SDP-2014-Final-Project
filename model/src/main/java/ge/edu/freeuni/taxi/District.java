package ge.edu.freeuni.taxi;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class District implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "latitude", column = @Column(name = "upper_latitude")),
			@AttributeOverride(name = "longitude", column = @Column(name = "upper_longitude")),
			@AttributeOverride(name = "name", column = @Column(name = "upper_location_name"))
	})
	private Location upperLeftCorner;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "latitude", column = @Column(name = "lower_latitude")),
			@AttributeOverride(name = "longitude", column = @Column(name = "lower_longitude")),
			@AttributeOverride(name = "name", column = @Column(name = "lower_location_name"))
	})
	private Location lowerRightCorner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getUpperLeftCorner() {
		return upperLeftCorner;
	}

	public void setUpperLeftCorner(Location upperLeftCorner) {
		this.upperLeftCorner = upperLeftCorner;
	}

	public Location getLowerRightCorner() {
		return lowerRightCorner;
	}

	public void setLowerRightCorner(Location lowerRightCorner) {
		this.lowerRightCorner = lowerRightCorner;
	}

	public boolean contains(Location location) {
		throw new NotImplementedException();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}