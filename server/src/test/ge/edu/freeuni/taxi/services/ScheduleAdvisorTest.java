package ge.edu.freeuni.taxi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.manager.DistrictManager;
import ge.edu.freeuni.taxi.manager.DriversManager;
import ge.edu.freeuni.taxi.manager.OrderManager;
import ge.edu.freeuni.taxi.District;
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
	private int allDrivers;

	@Before
	public void init() {
		scheduleAdvisor = new ScheduleAdvisor();
		allDistricts = new Random().nextInt(5);
		allDrivers = new Random().nextInt(10);

		OrderManager orderManager = Mockito.mock(OrderManager.class);
		Mockito.when(orderManager.filterOrders(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(District.class))).thenReturn(getOrders());

		DistrictManager districtManager = Mockito.mock(DistrictManager.class);
		Mockito.when(districtManager.getAllDistricts()).thenReturn(getDistricts());

		scheduleAdvisor.orderManager = orderManager;
		scheduleAdvisor.districtManager = districtManager;
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

	@Test
	public void testGetAdviseWhenThereShouldBeEqualNumberOfDriversInAnyDistinct() {
		DriversManager driversManager = Mockito.mock(DriversManager.class);
		Mockito.when(driversManager.getAllDrivers()).thenReturn(getAllDrivers());
		scheduleAdvisor.driversManager = driversManager;

		for (int numOfDrivers : scheduleAdvisor.getAdvise().getAreaToNumOfDrivers().values()) {
			assertEquals(allDrivers/allDistricts, numOfDrivers);
		}
	}

	private List<Driver> getAllDrivers() {
		List<Driver> drivers = new ArrayList<>();
		for (int i = 0; i < allDrivers; i++) {
			drivers.add(new Driver());
		}
		return drivers;
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
}