package ge.edu.freeuni.taxi;

import javax.persistence.Entity;

@Entity
public class DriversDuty {

	private long driversID;
	private String lastWorkingDate;
	private int isWorkingNow;
	
	public DriversDuty() {
		
	}
	
	
	public String getLastWorkingDate() {
		return lastWorkingDate;
	}

	public void setLastWorkingDate(String lastWorkingDate) {
		this.lastWorkingDate = lastWorkingDate;
	}

	public int getIsWorkingNow() {
		return isWorkingNow;
	}

	public void setIsWorkingNow(int isWorkingNow) {
		this.isWorkingNow = isWorkingNow;
	}

	

	public long getDriversID() {
		return driversID;
	}

	public void setDriversID(long driversID) {
		this.driversID = driversID;
	}
}
