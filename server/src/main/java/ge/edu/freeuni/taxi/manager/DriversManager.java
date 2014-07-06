package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.db.EMFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class DriversManager {

	private static DriversManager instance;

	private EntityManager em;

	private DriversManager() {
		this.em = EMFactory.createEM();
	}

	public static DriversManager getInstance() {
		if (instance == null) {
			instance = new DriversManager();
		}
		return instance;
	}

	public List<Driver> getAllDrivers() {
		return em.createQuery("SELECT d FROM Driver d" , Driver.class).getResultList();
	}

    public List<Driver> getAvailableDrivers() {
		return em.createQuery("SELECT d FROM Driver d WHERE d.available = True", Driver.class).getResultList();
	}

	public List<Driver> getWorkingDrivers() {
			return em.createQuery("SELECT d FROM Driver d WHERE d.working = True", Driver.class).getResultList();
	}

	public Driver updateDriver(Driver driver) {
		em.getTransaction().begin();

		em.merge(driver);

		em.getTransaction().commit();

		return driver;
	}

	public void deleteDriver(Driver driver) {
		em.getTransaction().begin();

		em.remove(driver);

		em.getTransaction().commit();
	}
}