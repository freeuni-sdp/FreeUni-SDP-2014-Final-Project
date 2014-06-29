package ge.edu.freeuni.taxi.services;


import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.SchedulingAdvice;
import ge.edu.freeuni.taxi.manager.OrderManager;
import ge.edu.freeuni.taxi.managers.DistrictManager;
import ge.edu.freeuni.taxi.managers.DriversManager;
import ge.edu.freeuni.taxi.map.District;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAdvisor {

	private OrderManager orderManager = OrderManager.getInstance();
	private DistrictManager districtManager = DistrictManager.getInstance();

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

			// TODO filter last day orders
			List<PassengerOrder> ordersByDistrict = orderManager.filterOrders(null, null, district.getId());
			allOrders += ordersByDistrict.size();
			ordersByArea.put(district, ordersByDistrict);
		}

		Map<District, Integer> districtIntegerMap = new HashMap<>();
		for (Map.Entry<District, List<PassengerOrder>> entry : ordersByArea.entrySet()) {
			districtIntegerMap.put(entry.getKey(), getCalculatedNumOfDrivers(allOrders, entry.getValue().size()));
		}

		advice.setAreaToNumOfDrivers(districtIntegerMap);
		return advice;
	}

	private Integer getCalculatedNumOfDrivers(int allOrdersSize, int ordersByDistrictSize) {
		int allDriversSize = DriversManager.getInstance().getAllDrivers().size();

		return allDriversSize*(ordersByDistrictSize/allOrdersSize);
	}
}
