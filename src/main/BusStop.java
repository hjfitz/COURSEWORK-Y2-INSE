package main;

public class BusStop {
	private String busName;
	private String time;
	private float travelTime;

	public BusStop(String busName, String time) {
		this.setBusName(busName);
		this.setTime(time);
		travelTime = 0;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

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

			String calculatedTime = Float.toString(travelTime) + " " + unit;

			return calculatedTime;
		}
	}
	
	public String calculateCost(String btime) {
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
		float travelCost = (float) (0.6 * travelTime);

		return Float.toString(travelCost) + "p";
	}

	public int convertToSeconds(int seconds, int minutes, int hours) {
		return seconds + (60 * minutes) + (3600 * hours);
	}
	
}
