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
    private String name;
    private Location location;
	private String driversID;

	public Driver() {}

    public Driver(String name, Location location) {
        this.location = location;
        this.name = name;
    }

    public String getDriversID() {
		return driversID;
	}

	public void setDriversID(String driversID) {
		this.driversID = driversID;
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

    @Override
    public boolean equals(Object obj) {
        return ((Driver)obj).getName().equals(name);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name=" + name +
                " location=" + location.getName() + "  " + location.getLast_update();
    }
}
