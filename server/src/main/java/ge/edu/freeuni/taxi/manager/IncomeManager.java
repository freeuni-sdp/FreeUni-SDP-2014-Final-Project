package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.DriverIncome;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.db.EMFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	private IncomeManager() {
		em = EMFactory.createEM();
	}

	public List<DriverIncome> getDriversIncome() {
		List<DriverIncome> incomes = new ArrayList<>();

		List<Driver> drivers = em.createQuery("SELECT d FROM Driver d", Driver.class).getResultList();

		for (Driver driver : drivers) {
			List<PassengerOrder> orders = em.createQuery("SELECT o FROM PassengerOrder o WHERE o.driver.id = :driverId", PassengerOrder.class)
					.setParameter("driverId", driver.getId()).getResultList();

			double income = 0;
			for (PassengerOrder order : orders) {
				income += order.getAmount();
			}

			DriverIncome driverIncome = new DriverIncome();
			driverIncome.setDriver(driver);
			driverIncome.setAmount(income);

			incomes.add(driverIncome);
		}

		Collections.sort(incomes, new Comparator<DriverIncome>() {
			@Override
			public int compare(DriverIncome o1, DriverIncome o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}
				return (int) (o1.getAmount() - o2.getAmount());
			}
		});

		return incomes;
	}
}