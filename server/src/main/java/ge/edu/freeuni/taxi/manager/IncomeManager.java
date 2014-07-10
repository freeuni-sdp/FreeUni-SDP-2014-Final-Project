package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.db.EMFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class IncomeManager {

	private static IncomeManager instance;

	public static IncomeManager getInstance() {
		if (instance == null) {
			instance = new IncomeManager();
		}
		return instance;
	}

	private EntityManager em;

	private Logger logger = LoggerFactory.getLogger(IncomeManager.class);

	private IncomeManager() {
		em = EMFactory.createEM();
	}

	public void adjustDriversIncome(List<Driver> drivers) {

		for (Driver driver : drivers) {
			adjustDriverIncome(driver);
		}
	}

	public void adjustDriverIncome(Driver driver) {
		List<PassengerOrder> orders = em.createQuery("SELECT o FROM PassengerOrder o WHERE o.driver.id = :driverId", PassengerOrder.class)
				.setParameter("driverId", driver.getId()).getResultList();

		double income = 0;
		for (PassengerOrder order : orders) {
			income += order.getAmount();
		}

		driver.setIncome(income);
		logger.info("adjusted driver income, driver id = '" + driver.getId() + "', income = '" + income + "'");
	}
}