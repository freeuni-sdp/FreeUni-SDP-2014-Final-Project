package ge.edu.freeuni.taxi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DriversDuty implements Serializable{

	@Id
	private String driversID;
	
	private long lastWorkingDate;
	private int isWorkingNow;
	
	public DriversDuty() {
		
	}
	
	
	public long getLastWorkingDate() {
		return lastWorkingDate;
	}

	public void setLastWorkingDate(long lastWorkingDate) {
		this.lastWorkingDate = lastWorkingDate;
	}

	public int getIsWorkingNow() {
		return isWorkingNow;
	}

	public void setIsWorkingNow(int isWorkingNow) {
		this.isWorkingNow = isWorkingNow;
	}

	
	public String getDriversID() {
		return driversID;
	}

	public void setDriversID(String driversID) {
		this.driversID = driversID;
	}
}
