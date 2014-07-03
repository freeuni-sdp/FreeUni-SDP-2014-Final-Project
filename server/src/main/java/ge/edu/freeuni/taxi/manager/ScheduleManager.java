package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.DriversDuty;
import ge.edu.freeuni.taxi.db.EMFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;


public class ScheduleManager {

	private static ScheduleManager instance;

	private EntityManager em;

	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	public static ScheduleManager getInstance() {
		if (instance == null) {
			instance = new ScheduleManager();
		}
		return instance;
	}

	private ScheduleManager() {
		em = EMFactory.createEM();
		fillDriversDutyDB();
	}

	

	private void fillDriversDutyDB(){
		List<Driver> allDrivers = em.createQuery("SELECT o FROM Driver o", Driver.class).getResultList();
		for(int i = 0; i < allDrivers.size(); i++){
			DriversDuty newOne = new DriversDuty();
			newOne.setDriversID(allDrivers.get(i).getDriversID());
			newOne.setIsWorkingNow(0);
			newOne.setLastWorkingDate(0);
		}
	}

	/**
	 * @return requested drivers
	 */
	public List<Driver> getWorkingDrivers(int num) {

		List<DriversDuty> driversDuty = em.createQuery("SELECT TOP " + num +  " o FROM DriversDuty o ORDER BY lastWorkingDate", DriversDuty.class).getResultList();
		List<Driver> drivers = new ArrayList<>();
		for(int i = 0; i < driversDuty.size(); i++){
			drivers.add(em.find(Driver.class, driversDuty.get(i).getDriversID()));
		}
		
		setWorkingState(driversDuty);
		setLastWorkingDate(driversDuty);
		
		return drivers;

	}
	
	
	private void setLastWorkingDate(List<DriversDuty> driversDuties) {
		for(int i = 0; i < driversDuties.size(); i++){
			DriversDuty curr = driversDuties.get(i);
			
			curr.setLastWorkingDate(StringToLong(sdf.format(new Date())));
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}
	}

	private void setWorkingState(List<DriversDuty> driversDuties){
		List<DriversDuty> workingDrivers = em.createQuery("SELECT o FROM DriversDuty o WHERE isWorkingNow = 1", DriversDuty.class).getResultList();
		
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
	
	
	
	public long StringToLong(String dateString){
		SimpleDateFormat format = sdf;
		
		try {
			return  format.parse(dateString).getTime();
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			return -1;
		}

	}
	
	public String LongToString(long dateLong){
		
		return sdf.format(new Date(dateLong));
	}

}