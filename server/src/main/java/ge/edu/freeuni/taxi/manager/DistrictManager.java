package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.map.District;

import java.util.ArrayList;
import java.util.List;

public class DistrictManager {

	private static DistrictManager instance;

	private DistrictManager() {	}

	public static DistrictManager getInstance() {
		if (instance == null) {
			instance = new DistrictManager();
		}
		return instance;
	}

	public List<District> getAllDistricts() {
		// TODO implement
		return new ArrayList<>();
	}
}