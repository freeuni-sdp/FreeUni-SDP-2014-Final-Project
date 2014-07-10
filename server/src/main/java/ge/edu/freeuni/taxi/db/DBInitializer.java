package ge.edu.freeuni.taxi.db;

import ge.edu.freeuni.taxi.*;
import ge.edu.freeuni.taxi.manager.ScheduleManager;

import javax.persistence.EntityManager;
import java.util.Date;

public class DBInitializer {

	public static void main(String [] args) {
		EntityManager em = EMFactory.createEM();
        em.getTransaction().begin();
		for (int i = 0; i < 10; i++) {
			Driver driver = getDriver(getLocation(i), i);
			em.persist(driver);
            Passenger passenger = getPassanger(getLocation(i), i);
            em.persist(passenger);
            PassengerOrder passengerOrder = getPassengerOrder(passenger, driver, getLocation(i), i);
            em.persist(passengerOrder);
		}
		em.getTransaction().commit();
		EMFactory.close();
    	ScheduleManager manager = ScheduleManager.getInstance();

		addInitialDistricts(em);
	}

    private static PassengerOrder getPassengerOrder(Passenger passenger, Driver driver, Location location, int i) {
        PassengerOrder passengerOrder = new PassengerOrder();
        passengerOrder.setPassenger(passenger);
        if (i < 5) {
            passengerOrder.setDriver(driver);
        } else {
            passengerOrder.setIncoming(true);
        }
        passengerOrder.setDestination(location);
        passengerOrder.setCreateTime(new Date());
        passengerOrder.setAmount(10);
        passengerOrder.setActive(true);
        return passengerOrder;
    }

    private static Passenger getPassanger(Location location, int i) {
        Passenger passenger = new Passenger();
        passenger.setLocation(location);
        passenger.setInfo("Passenger" + i);
        return passenger;
    }

    private static Driver getDriver(Location location, int i) {
        Driver driver = new Driver();
        driver.setLocation(location);
        if (i < 5) {
            driver.setAvailable(false);
        } else {
            driver.setAvailable(true);
        }
        driver.setWorking(true);
        driver.setName("Driveriiiii #" + i);
        driver.setLastWorkingDate(new Date());
        driver.setLocationLastUpdateTime(new Date());
        return driver;
    }

    private static Location getLocation(int i) {
        Location location = new Location();
        location.setName("Location #" + i);
        location.setLatitude(4000l + i * 100);
        location.setLongitude(4200l + i * 100);
        return location;
    }

    private static void addInitialDistricts(EntityManager em) {
		em.getTransaction().begin();

		District vakeSaburtalo = new District();
		vakeSaburtalo.setName("ვაკე-საბურთალო");
		vakeSaburtalo.setLowerRightCorner(new Location(41.816697, 44.691052));
		vakeSaburtalo.setUpperLeftCorner(new Location(41.778854, 44.767957));
		em.persist(vakeSaburtalo);

		District gldaniNadzaladevi = new District();
		gldaniNadzaladevi.setName("გლდანი-ნაძალადევი");
		gldaniNadzaladevi.setLowerRightCorner(new Location(41.838186, 44.768643));
		gldaniNadzaladevi.setUpperLeftCorner(new Location(41.706064, 44.894986));
		em.persist(gldaniNadzaladevi);

		District isaniSamgori = new District();
		isaniSamgori.setName("ისანი-სამგორი");
		isaniSamgori.setLowerRightCorner(new Location(41.705552, 44.846921));
		isaniSamgori.setUpperLeftCorner(new Location(41.660937, 44.980136));
		em.persist(isaniSamgori);

		District dzveliTbilisi = new District();
		dzveliTbilisi.setName("ძველი თბილისი");
		dzveliTbilisi.setLowerRightCorner(new Location(41.700938, 44.753537));
		dzveliTbilisi.setUpperLeftCorner(new Location(41.674786, 44.835248));
		em.persist(dzveliTbilisi);

		em.getTransaction().commit();
	}
}