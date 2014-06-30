package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.db.EMFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	public void deleteOrder(long id){
		
		List<PassengerOrder> orders = getOrders();
		for (PassengerOrder passengerOrder : orders) {
			if(passengerOrder.getId() == id){
				em.remove(passengerOrder);
				break;
			}
		}
	}
	
	/**
	 *	filters orders by createDate and district
	 *
	 * @param fromDate date from
	 * @param toDate date to
	 * @param districtId id of district
	 * @return orders
	 */
	public List<PassengerOrder> filterOrders(Date fromDate, Date toDate, Long districtId) {

		StringBuilder ql = new StringBuilder("FROM FROM PassengerOrder o WHERE 1=1");
		Map<String, Object> params = new HashMap<>();

		if (fromDate != null) {
			ql.append(" AND o.createTime > :fromDate");
			params.put("fromDate", fromDate);
		}

		if (toDate != null) {
			ql.append(" AND o.createTime < :toDate");
			params.put("toDate", toDate);
		}

		//noinspection StatementWithEmptyBody
		if (districtId != null) {
			// TODO implement, when exists connection between PassengerOrder and District
		}

		Query query = em.createQuery("SELECT o " + ql.toString(), PassengerOrder.class);

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		//noinspection unchecked
		return query.getResultList();
	}
}