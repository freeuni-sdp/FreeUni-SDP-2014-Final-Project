package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.District;
import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.db.EMFactory;
import org.hibernate.loader.custom.NonUniqueDiscoveredSqlAliasException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {

	private static OrderManager instance;

	private EntityManager em;

	private Logger logger = LoggerFactory.getLogger(OrderManager.class);

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

		em.merge(order.getPassenger());
		em.merge(order);

		em.getTransaction().commit();
		logger.info("updated order, order id = " + order.getId());
	}

	/**
	 * @return all orders
	 */
	public List<PassengerOrder> getOrders() {
		logger.info("getting orders");

		return em.createQuery("SELECT o FROM PassengerOrder o", PassengerOrder.class).getResultList();
	}
	
	public void deleteOrder(PassengerOrder order){
		
		em.getTransaction().begin();

		em.remove(order);

		em.getTransaction().commit();

		logger.info("deleted order - orderId = " + order.getId());
	}
	
	/**
	 *	filters orders by createDate and district
	 *
	 * @param fromDate date from
	 * @param toDate date to
	 * @param district district
	 * @return orders
	 */
	public List<PassengerOrder> filterOrders(Date fromDate, Date toDate, District district) {
		logger.info("filtering orders");

		StringBuilder ql = new StringBuilder("FROM PassengerOrder o WHERE 1=1");
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
		if (district != null) {
			ql.append(" AND o.passenger.location.longitude < :upperCornerLongitude " +
					"AND o.passenger.location.longitude > :lowerCornerLongitude " +
					"AND o.passenger.location.latitude > :upperCornerLatitude " +
					"AND o.passenger.location.latitude < :lowerCornerLatitude ");

			params.put("upperCornerLongitude", district.getUpperLeftCorner().getLongitude());
			params.put("lowerCornerLongitude", district.getLowerRightCorner().getLongitude());
			params.put("upperCornerLatitude", district.getUpperLeftCorner().getLatitude());
			params.put("lowerCornerLatitude", district.getLowerRightCorner().getLatitude());
		}

		Query query = em.createQuery("SELECT o " + ql.toString(), PassengerOrder.class);

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		//noinspection unchecked
		return query.getResultList();
	}

	public PassengerOrder getOrderByPassengerName(String passengerName) {
		logger.info("getting order by passenger name");
		Query query = em.createQuery("SELECT o FROM PassengerOrder o WHERE o.passenger.info = :name",PassengerOrder.class).setParameter("name", passengerName);
		try {
			return (PassengerOrder) query.getSingleResult();
		} catch (NonUniqueResultException ex) {

			logger.error("there are many orders by that passenger (passenger name is " + passengerName +")");
			return (PassengerOrder)query.getResultList().get(0);
		}
	}

    public List<PassengerOrder> getActiveOrders() {
		logger.info("getting active orders");

		return em.createQuery("SELECT o FROM PassengerOrder o WHERE o.active = TRUE AND o.incoming = FALSE ORDER BY o.createTime desc", PassengerOrder.class).getResultList();
	}

    public void createPassengerOrder(PassengerOrder passengerOrder) {
        em.getTransaction().begin();
        if (passengerOrder.getId() == null) {
            if (passengerOrder.getDriver() != null) {
                Driver driver = updateDriver(passengerOrder);
                passengerOrder.setDriver(driver);
            }
            em.persist(passengerOrder.getPassenger());
            passengerOrder.setActive(true);
            passengerOrder.setCreateTime(new Date());
            em.persist(passengerOrder);
        } else {
            PassengerOrder order = em.find(PassengerOrder.class, passengerOrder.getId());
            order.setIncoming(false);
            order.setDriver(updateDriver(passengerOrder));
            em.merge(order);
        }
        em.getTransaction().commit();

		logger.info("created passenger order");
    }

    private Driver updateDriver(PassengerOrder passengerOrder) {
        Driver driver = em.find(Driver.class, passengerOrder.getDriver().getId());
        driver.setAvailable(false);
        em.merge(driver);
        return driver;
    }

    public List<PassengerOrder> getIncomingOrders() {
        return em.createQuery("SELECT o FROM PassengerOrder o WHERE o.active = TRUE AND o.incoming = TRUE ORDER BY o.createTime desc", PassengerOrder.class).getResultList();
    }

    /**
     * ითვლის მძღოლის "მარგი ქმედების კოეფიციენტს"
     * @param driverID მძღოლის id
     * @param from დრო, საიდანაც იწყება მქკ-ს დათვლა
     * @param to დრო, სადამდეც ითვლება მქკ
     * @return
     */
    public double getFactor(Long driverID, Date from, Date to) {
        long sumWorkingMinutes = em.createQuery("SELECT SUM(o.duration) FROM PassengerOrder o " +
                                                " WHERE o.driver.id =:driverID" +
                                                " AND o.createTime BETWEEN :fromTime AND :toTime", Long.class)
                                                .setParameter("driverID", driverID)
                                                .setParameter("fromTime", from)
                                                .setParameter("toTime", to)
                                                .getSingleResult();

        return sumWorkingMinutes / ( (to.getTime() - from.getTime()) / (1000 * 60));
    }
}