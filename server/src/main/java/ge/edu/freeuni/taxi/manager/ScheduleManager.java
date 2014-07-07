package ge.edu.freeuni.taxi.manager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.db.EMFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;

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

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("aaa");
				controlWorkersSchedule();
			}
		};

		timer.schedule(task, 0, 60*60*8);
	}

	public void controlWorkersSchedule() {
		int count = em.createQuery("SELECT d FROM Driver d").getResultList().size();
		int num = count/3;
		List<Driver> drivers = em.createQuery("SELECT o FROM Driver o ORDER BY lastWorkingDate", Driver.class).setMaxResults(num).getResultList();

		setWorkingState(drivers);
		setLastWorkingDate(drivers);
	}

	private void setLastWorkingDate(List<Driver> drivers) {
		for (Driver curr : drivers) {
			curr.setLastWorkingDate(new Date());
			//curr.setLastWorkingDate(StringToLong(sdf.format(new Date())));
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}
	}

	private void setWorkingState(List<Driver> drivers){
		List<Driver> workingDrivers = em.createQuery("SELECT o FROM Driver o WHERE o.working = True", Driver.class).getResultList();

		for (Driver curr : workingDrivers) {
			curr.setWorking(false);
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}

		for (Driver curr : drivers) {
			curr.setWorking(true);
			em.getTransaction().begin();
			em.merge(curr);
			em.getTransaction().commit();
		}
	}
}