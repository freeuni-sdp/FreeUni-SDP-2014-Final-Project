package ge.edu.freeuni.taxi;

import ge.edu.freeuni.taxi.map.District;

import java.util.Map;

/**
 * pojo object, that will be used for transfer advise info while scheduling drivers
 */
public class SchedulingAdvice {

	private Map<District, Integer> areaToNumOfDrivers;

	public Map<District, Integer> getAreaToNumOfDrivers() {
		return areaToNumOfDrivers;
	}

	public void setAreaToNumOfDrivers(Map<District, Integer> areaToNumOfDrivers) {
		this.areaToNumOfDrivers = areaToNumOfDrivers;
	}
}
