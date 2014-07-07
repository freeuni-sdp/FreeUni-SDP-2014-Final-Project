package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.District;
import ge.edu.freeuni.taxi.db.EMFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class DistrictManager {

	private static DistrictManager instance;

	private EntityManager em;

	private DistrictManager() {
		em = EMFactory.createEM();
	}

	public static DistrictManager getInstance() {
		if (instance == null) {
			instance = new DistrictManager();
		}
		return instance;
	}

	public List<District> getAllDistricts() {
		return em.createQuery("SELECT d FROM District d", District.class).getResultList();
	}
}