package ge.edu.freeuni.taxi.dispatcher;

/**
 * @author Saba Gogolidze
 *
 * Class for finding nearest available driver from passenger.
 */

import java.util.List;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.manager.DriversManager;

public class NearestDriverFinder {

	private DriversManager driversManager;
	private NearestDriverFinder instance = null;

	private NearestDriverFinder() {
		driversManager = DriversManager.getInstance();
		;
	}

	public NearestDriverFinder getInstance() {
		if (instance == null) {
			instance = new NearestDriverFinder();
		}
		return instance;
	}

	public Driver getNearestDriver(Passenger passenger) {
		List<Driver> avaliableDrivers = driversManager.getAvailableDrivers();
		Driver nearesrtDriver = null;
		double smallestDistance = Double.MAX_VALUE;
		for (Driver driver : avaliableDrivers) {
			Location driverLocation = driver.getLocation();
			Location passangerLocation = passenger.getLocation();
			double dlat = driverLocation.getLatitude();
			double dlong = driverLocation.getLongitude();
			double plat = passangerLocation.getLatitude();
			double plong = passangerLocation.getLongitude();
			double distance = (plat - dlat) * (plat - dlat) + (plong - dlong)
					* (plong - dlong);
			if (distance <= smallestDistance) {
				smallestDistance = distance;
				nearesrtDriver = driver;
			}
		}
		return nearesrtDriver;
	}

}
