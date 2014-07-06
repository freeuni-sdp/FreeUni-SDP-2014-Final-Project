package ge.edu.freeuni.taxi.services;

import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.SchedulingAdvice;
import ge.edu.freeuni.taxi.manager.OrderManager;
import ge.edu.freeuni.taxi.manager.DistrictManager;
import ge.edu.freeuni.taxi.manager.DriversManager;
import ge.edu.freeuni.taxi.District;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAdvisor {

	OrderManager orderManager = OrderManager.getInstance();
	DistrictManager districtManager = DistrictManager.getInstance();
	DriversManager driversManager = DriversManager.getInstance();

	/**
	 * according to the last day's statistics, that method returns advise,
	 * that collects information about how many drivers is need in any district today
	 * @return advice
	 */
	public SchedulingAdvice getAdvise() {
		SchedulingAdvice advice = new SchedulingAdvice();
		Map<District, List<PassengerOrder>> ordersByArea = new HashMap<>();
		int allOrders = 0;
		for (District district : districtManager.getAllDistricts()) {

			List<PassengerOrder> orders = orderManager.filterOrders(getYesterdayDate(), new Date(), district);
			allOrders += orders.size();
			ordersByArea.put(district, orders);
		}

		Map<District, Integer> districtIntegerMap = new HashMap<>();
		for (Map.Entry<District, List<PassengerOrder>> entry : ordersByArea.entrySet()) {
			districtIntegerMap.put(entry.getKey(), getCalculatedNumOfDrivers(allOrders, entry.getValue().size()));
		}

		advice.setAreaToNumOfDrivers(districtIntegerMap);
		return advice;
	}

	protected Date getYesterdayDate() {
		Date yesterday = new Date();
		yesterday.setDate(yesterday.getDate() - 1);
		return yesterday;
	}

	private int getCalculatedNumOfDrivers(double allOrdersSize, double ordersByDistrictSize) {
		double allDriversSize = driversManager.getAllDrivers().size();

		return new Double(allDriversSize*ordersByDistrictSize/allOrdersSize).intValue();
	}
}