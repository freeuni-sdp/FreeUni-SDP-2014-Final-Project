package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.db.EMFactory;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class OrderManager {

	private static OrderManager instance;

	private EntityManager em;

	public static OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}

	private OrderManager() {
		em = EMFactory.createEM();
	}

	/**
	 * updates order object
	 * merges if its exists in db
	 * persists otherwise
	 * @param order order to update
	 */
	public void updateOrder(PassengerOrder order) {
		em.getTransaction().begin();
		if (order.getId() != null) {
			System.out.println("Merging Order");
			em.merge(order);
		} else {
			System.out.println("Persisting Order");
			if (order.getPassenger().getId() == null) {
				em.persist(order.getPassenger());
			}
			em.persist(order);
		}
		em.getTransaction().commit();
	}

	/**
	 * @return all orders
	 */
	public List<PassengerOrder> getOrders() {
		return em.createQuery("SELECT o FROM PassengerOrder o", PassengerOrder.class).getResultList();
	}

	/**
	 *	filters orders by createDate
	 * @param fromDate date from
	 * @param toDate date to
	 * @return orders
	 */
	public List<PassengerOrder> getOrdersByDate(Date fromDate, Date toDate) {
		return em.createQuery("SELECT o FROM PassengerOrder o WHERE o.createTime > :fromDate AND o.createTime < :toDate", PassengerOrder.class)
				.setParameter("fromDate", fromDate).setParameter("toDate", toDate).getResultList();
	}
}