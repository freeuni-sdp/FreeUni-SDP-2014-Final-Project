package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.db.EMFactory;
import ge.edu.freeuni.taxi.Driver;

import javax.persistence.EntityManager;

public class LocationManager {
	
	private static LocationManager instance;

	private EntityManager em;

	public static LocationManager getInstance() {
		if (instance == null) {
			instance = new LocationManager();
		}
		return instance;
	}

	private LocationManager() {
		em = EMFactory.createEM();
	}

}
