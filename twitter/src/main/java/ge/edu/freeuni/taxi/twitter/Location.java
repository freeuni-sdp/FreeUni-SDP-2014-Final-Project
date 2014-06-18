package ge.edu.freeuni.taxi.twitter;

/**
 * @author Sandro Dolidze
 *
 * Simple class for saving geo location.
 */
public class Location {
    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}