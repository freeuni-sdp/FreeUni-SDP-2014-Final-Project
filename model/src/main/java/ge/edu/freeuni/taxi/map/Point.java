package ge.edu.freeuni.taxi.map;

import javax.persistence.Embeddable;

@Embeddable
public class Point {

	private long longitude;

	private long latitude;

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
}