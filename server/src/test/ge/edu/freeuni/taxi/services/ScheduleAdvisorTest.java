package ge.edu.freeuni.taxi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.manager.DistrictManager;
import ge.edu.freeuni.taxi.manager.OrderManager;
import ge.edu.freeuni.taxi.map.District;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ScheduleAdvisorTest {

	private ScheduleAdvisor scheduleAdvisor;

	private int allDistricts;

	@Before
	public void init() {
		scheduleAdvisor = new ScheduleAdvisor();
		allDistricts = new Random().nextInt(15);

		OrderManager orderManager = Mockito.mock(OrderManager.class);
		Mockito.when(orderManager.filterOrders(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyLong())).thenReturn(getOrders());

		DistrictManager districtManager = Mockito.mock(DistrictManager.class);
		Mockito.when(districtManager.getAllDistricts()).thenReturn(getDistricts());

		scheduleAdvisor.orderManager = orderManager;
		scheduleAdvisor.districtManager = districtManager;
	}

	private List<District> getDistricts() {
		List<District> districts = new ArrayList<>();
		for (int i = 0; i < allDistricts; i++) {
			districts.add(new District());
		}
		return districts;
	}

	private List<PassengerOrder> getOrders() {
		List<PassengerOrder> passengerOrders = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			PassengerOrder order = new PassengerOrder();
			order.setCreateTime(scheduleAdvisor.getYesterdayDate());
			passengerOrders.add(order);
		}
		return passengerOrders;
	}

	@Test
	public void testGetAdviseNotNull() {
		assertNotEquals(scheduleAdvisor.getAdvise(), null);
	}

	@Test
	public void testGetAdviseWhenMapMustNotBeNull() {
		assertNotEquals(scheduleAdvisor.getAdvise().getAreaToNumOfDrivers(), null);
	}

	@Test
	public void testGetAdviseWhenMapSizeMustBeEqualsToAllDistricts() {
		assertEquals(allDistricts, scheduleAdvisor.getAdvise().getAreaToNumOfDrivers().size());
	}
}