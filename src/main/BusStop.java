package main;

public class BusStop {
	private String busName;
	private String time;
	
	
	public BusStop(String busName, String time){
		this.setBusName(busName);
		this.setTime(time);
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
}
