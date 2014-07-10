package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.District;
import ge.edu.freeuni.taxi.db.EMFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class DistrictManager {

	private static DistrictManager instance;

	private EntityManager em;

	private Logger logger = LoggerFactory.getLogger(DistrictManager.class);

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
		logger.info("getting all Districts");
		return em.createQuery("SELECT d FROM District d", District.class).getResultList();
	}
}