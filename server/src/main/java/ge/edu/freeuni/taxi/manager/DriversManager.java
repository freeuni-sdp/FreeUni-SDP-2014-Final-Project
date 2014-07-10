package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.db.EMFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class DriversManager {

	private static DriversManager instance;

	private EntityManager em;

	private Logger logger = LoggerFactory.getLogger(DriversManager.class);

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
		List<Driver> result = em.createQuery("SELECT d FROM Driver d" , Driver.class).getResultList();
		IncomeManager.getInstance().adjustDriversIncome(result);

		logger.info("getting all drivers...");
		return result;
	}

    public List<Driver> getAvailableDrivers() {
		List<Driver> result = em.createQuery("SELECT d FROM Driver d WHERE d.available = True and d.working = True", Driver.class).getResultList();
		IncomeManager.getInstance().adjustDriversIncome(result);

		logger.info("getting all available drivers");
		return result;
	}

    public List<Driver> getWorkingDrivers() {
		List<Driver> result = em.createQuery("SELECT d FROM Driver d WHERE d.working = True", Driver.class).getResultList();
		IncomeManager.getInstance().adjustDriversIncome(result);

		logger.info("getting all working drivers");
		return result;
	}

    public Driver addDriver(Driver driver) {
        em.getTransaction().begin();
        em.persist(driver);
        em.getTransaction().commit();
		logger.info("adding a driver");
        return driver;
    }

    public void deleteDriver(long id) {
        em.getTransaction().begin();
        Driver driver = em.find(Driver.class, id);
        em.remove(driver);
        em.getTransaction().commit();

		logger.info("deleting a driver with id = " + id);
    }

    public Driver updateDriverName(long id, String name) {
        em.getTransaction().begin();
        Driver driver = em.find(Driver.class, id);
		String oldName = driver.getName();
		driver.setName(name);
        em.merge(driver);
        em.getTransaction().commit();

		IncomeManager.getInstance().adjustDriverIncome(driver);
		logger.info("updating driver name from '" + oldName + "' to '" + name +"'");
        return driver;
    }
    public Driver updateDriverAvailability(long id, boolean available) {
		em.getTransaction().begin();
		Driver driver = em.find(Driver.class, id);
		driver.setAvailable(available);
		em.merge(driver);
		em.getTransaction().commit();

		IncomeManager.getInstance().adjustDriverIncome(driver);
		logger.info("updating driver availability for driver - '" + driver.getName() + "'; set " + (available ? "available" : "not available"));
		return driver;
	}

    public Driver updateDriverLocation(long id, Location location) {
        em.getTransaction().begin();
        Driver driver = em.find(Driver.class, id);
        driver.setLocation(location);
        em.merge(driver);
        em.getTransaction().commit();

		IncomeManager.getInstance().adjustDriverIncome(driver);
		logger.info("updating driver location - driver - " + driver.getName());
		return driver;
    }

    public Driver getDriver(String name) {
		Driver result = em.createQuery("SELECT d FROM Driver d WHERE d.name = :name", Driver.class).setParameter("name", name).getSingleResult();
		IncomeManager.getInstance().adjustDriverIncome(result);

		logger.info("getting a driver with name = '" + name + "'");
		return result;
	}

    public Driver getDriver(Long id) {
		logger.info("getting a driver with id = '" + id + "'");
        return em.find(Driver.class, id);
    }
}