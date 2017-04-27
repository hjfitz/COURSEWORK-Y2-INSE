package main;

/**
 * 
 * @author FlashCloud
 * 
 *  initalizes the bus stop values such as busName, time and travelTime
 *  Has core functions to calculate travel time and travel cost
 *
 */
public class BusStop {
	private String busName;
	private String time;
	private int travelTime;

	public BusStop(String busName, String time) {
		this.setBusName(busName);
		this.setTime(time);
		travelTime = 0;
	}

	/**
	 * gets the time of bus stop
	 * @return time
	 */
	public String getTime() {
		return time;
	}
	
	/***
	 * sets the time of the bus stop
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/***
	 * gets the name of the bus stop
	 * @return busName
	 */
	public String getBusName() {
		return busName;
	}

	/***
	 * set the bus stop name
	 * @param busName
	 */
	public void setBusName(String busName) {
		this.busName = busName;
	}

	/***
	 * Calculates the travel time of the journey
	 * @param btime time of the arrival bus stop
	 * @return formatted string as correct journey time
	 */
	public String calculateTravelTime(String btime) {
		String[] fromStop = time.split(":");
		String[] toStop = btime.split(":");

		int fromhour = Integer.parseInt(fromStop[0]);
		int fromminute = Integer.parseInt(fromStop[1]);
		int fromsecond = Integer.parseInt(fromStop[2]);

		int tempfrom;
		tempfrom = convertToSeconds(fromsecond, fromminute, fromhour);

		int tohour = Integer.parseInt(toStop[0]);
		int tominute = Integer.parseInt(toStop[1]);
		int tosecond = Integer.parseInt(toStop[2]);

		int tempto;
		tempto = convertToSeconds(tosecond, tominute, tohour);

		travelTime = (tempto - tempfrom) / 60;

		String unit = "";
		if (travelTime > 60) {
			travelTime /= 60;
			unit = "hours";
		} else {
			unit = "minutes";
		}
		if (travelTime <= 0) {
			return "past";
		} else {

			String calculatedTime = travelTime + " " + unit;

			return calculatedTime;
		}
	}
	
	/***
	 * calculates the price of the ticket for journey
	 * @param time of the journey
	 * @return price of the ticket
	 */
	public String calculateCost(int journeyTime) {
		int travelCost = (int) (0.6 * journeyTime);
		return travelCost + "p";
	}
	
	/***
	 * returns the travel time of the journey
	 * @return travel time
	 */
	public int getTravelTime(){
		return travelTime;
	}

	/***
	 * converts the given values into a seconds
	 * @param seconds 
	 * @param minutes
	 * @param hours
	 * @return seconds
	 */
	public int convertToSeconds(int seconds, int minutes, int hours) {
		return seconds + (60 * minutes) + (3600 * hours);
	}
	
}
