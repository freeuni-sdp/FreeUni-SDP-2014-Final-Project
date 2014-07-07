package ge.edu.freeuni.taxi;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Location implements Serializable {

	private String name;

	private double longitude;

	private double latitude;

	public Location() {
	}

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}