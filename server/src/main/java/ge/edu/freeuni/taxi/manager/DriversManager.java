package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
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
        return em.createQuery("SELECT d FROM Driver d WHERE d.available = True and d.working = True", Driver.class).getResultList();
    }

    public List<Driver> getWorkingDrivers() {
        return em.createQuery("SELECT d FROM Driver d WHERE d.working = True", Driver.class).getResultList();
    }

    public Driver addDriver(Driver driver) {
        em.getTransaction().begin();
        em.persist(driver);
        em.getTransaction().commit();
        return driver;
    }

    public void deleteDriver(long id) {
        em.getTransaction().begin();
        Driver driver = em.find(Driver.class, id);
        em.remove(driver);
        em.getTransaction().commit();
    }

    public Driver updateDriverName(long id, String name) {
        em.getTransaction().begin();
        Driver driver = em.find(Driver.class, id);
        driver.setName(name);
        em.merge(driver);
        em.getTransaction().commit();
        return driver;
    }
    public Driver updateDriverAvailability(long id, boolean available) {
    	 em.getTransaction().begin();
         Driver driver = em.find(Driver.class, id);
         driver.setAvailable(available);
         em.merge(driver);
         em.getTransaction().commit();
         return driver;
    }
    
    

    public Driver updateDriverLocation(long id, Location location) {
        em.getTransaction().begin();
        Driver driver = em.find(Driver.class, id);
        driver.setLocation(location);
        em.merge(driver);
        em.getTransaction().commit();
        return driver;
    }

    public Driver getDriver(String name) {
        return em.createQuery("SELECT d FROM Driver d WHERE d.name = :name", Driver.class).setParameter("name", name).getSingleResult();
    }
}