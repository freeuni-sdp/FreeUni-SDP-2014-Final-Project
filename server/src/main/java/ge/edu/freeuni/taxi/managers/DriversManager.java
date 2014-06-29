package ge.edu.freeuni.taxi.managers;

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

	public List getAllDrivers() {
		// TODO implement
		return new ArrayList();
	}
}
