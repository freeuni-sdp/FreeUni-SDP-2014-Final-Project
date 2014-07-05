package ge.edu.freeuni.taxi.db;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;

import javax.persistence.EntityManager;
import java.util.Date;

public class DBInitializer {

	public static void main(String [] args) {
		EntityManager em = EMFactory.createEM();
		em.getTransaction().begin();
		for (int i = 0; i < 10; i++) {
			Driver driver = new Driver();

			Location location = new Location();
			location.setName("Location #" + i);
			location.setLatitude(4000l + i * 100);
			location.setLongitude(4200l + i * 100);

			driver.setLocation(location);
			driver.setAvailable(true);
			driver.setName("Driver #" + i);
			driver.setLastWorkingDate(new Date());
			driver.setLocationLastUpdateTime(new Date());

			em.persist(driver);
		}
		em.getTransaction().commit();
		EMFactory.close();
	}
}
