package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.DriversDuty;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.db.EMFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mysql.jdbc.Driver;

public class ScheduleManager {

	private static ScheduleManager instance;

	private EntityManager em;

	public static ScheduleManager getInstance() {
		if (instance == null) {
			instance = new ScheduleManager();
		}
		return instance;
	}

	private ScheduleManager() {
		em = EMFactory.createEM();
	}

	

	/**
	 * @return requested drivers
	 */
	public List<Driver> getWorkingDrivers(int num) {
		
		List<DriversDuty> driversDuty = em.createQuery("SELECT TOP " + num +  " * FROM DriversDuty ORDER BY lastWorkingDate", DriversDuty.class).getResultList();
		
	//	List<DriversDuty> driversDuty2 = em.createQuery("SELECT * FROM DriversDuty ORDER BY lastWorkingDate LIMIT " + num, DriversDuty.class).getResultList();

		List<Driver> drivers = new ArrayList<>();
		for(int i = 0; i < driversDuty.size(); i++){
			drivers.add(em.find(Driver.class, driversDuty.get(i).getDriversID()));
		}
		
		setWorkingState(driversDuty);
		
		return drivers;

	}
	
	
	private void setWorkingState(List<DriversDuty> driversDuties){
		List<DriversDuty> workingDrivers = em.createQuery("SELECT * FROM DriversDuty WHERE isWorkingNow = 1", DriversDuty.class).getResultList();
		
		for(int i = 0; i < workingDrivers.size(); i++){
			DriversDuty curr = workingDrivers.get(i);
			curr.setIsWorkingNow(0);
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}
		
		
		
		for(int i = 0; i < driversDuties.size(); i++){
			DriversDuty curr = driversDuties.get(i);
			curr.setIsWorkingNow(1);
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}
		
		
		
	}
}
