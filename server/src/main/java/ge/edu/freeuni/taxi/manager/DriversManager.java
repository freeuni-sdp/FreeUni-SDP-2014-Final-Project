package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriversManager {

	private static DriversManager instance;

	private DriversManager() {}

	public static DriversManager getInstance() {
		if (instance == null) {
			instance = new DriversManager();
		}
		return instance;
	}

	public List<Driver> getAllDrivers() {
		return new ArrayList();
	}

    public List<Driver> getOnlineDrivers() {

        return new ArrayList();
    }
}
