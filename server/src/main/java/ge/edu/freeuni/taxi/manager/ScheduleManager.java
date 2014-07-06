package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.db.EMFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				controlWorkersSchedule();
			}
		};

		timer.schedule(task, 0, 60*60*8);
	}

	public void controlWorkersSchedule() {
		long count = (Long) em.createNativeQuery("SELECT count(1) FROM Driver").getSingleResult();
		long num = count/3;
		List<Driver> drivers = em.createQuery("SELECT TOP " + num +  " o FROM Driver o ORDER BY lastWorkingDate", Driver.class).getResultList();

		setWorkingState(drivers);
		setLastWorkingDate(drivers);
	}


	private void setLastWorkingDate(List<Driver> drivers) {
		for(int i = 0; i < drivers.size(); i++){
			Driver curr = drivers.get(i);

			curr.setLastWorkingDate(new Date());
			//curr.setLastWorkingDate(StringToLong(sdf.format(new Date())));
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}
	}

	private void setWorkingState(List<Driver> drivers){
		List<Driver> workingDrivers = em.createQuery("SELECT o FROM Driver o WHERE o.working = True", Driver.class).getResultList();

		for(int i = 0; i < workingDrivers.size(); i++){
			Driver curr = workingDrivers.get(i);
			curr.setWorking(false);
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}



		for(int i = 0; i < drivers.size(); i++){
			Driver curr = drivers.get(i);
			curr.setWorking(true);
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}

	}
}