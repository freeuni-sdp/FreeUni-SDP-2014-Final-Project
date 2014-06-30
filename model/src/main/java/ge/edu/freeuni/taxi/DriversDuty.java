package ge.edu.freeuni.taxi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class DriversDuty implements Serializable{

	private long driversID;
	private Date lastWorkingDate;
	private int isWorkingNow;
	
	public DriversDuty() {
		
	}
	
	
	public Date getLastWorkingDate() {
		return lastWorkingDate;
	}

	public void setLastWorkingDate(Date lastWorkingDate) {
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
