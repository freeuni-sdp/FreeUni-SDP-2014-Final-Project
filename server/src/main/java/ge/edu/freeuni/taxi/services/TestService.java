package ge.edu.freeuni.taxi.services;

import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.User;
import ge.edu.freeuni.taxi.db.EMFactory;
import ge.edu.freeuni.taxi.manager.OrderManager;

import javax.persistence.EntityManager;
import java.util.Date;

public class TestService {

	private static EntityManager em = EMFactory.createEM();

	public static String getTestMessage() {
		User user = em.find(User.class, 1l);

		return (user != null) ? user.getName() : "you";
	}
}
