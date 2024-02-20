package taxibooking.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Taxi {

	private int taxiNo;
	private int userId;
	private float totalEarnings;
	private char currentStopPoint;
	private  LocalTime  availableTime;

	public Taxi(int taxiNo) {
		this.taxiNo = taxiNo;
		this.totalEarnings = 0.0f;
		this.availableTime = LocalTime.of(1,0);
		this.currentStopPoint = 'A';
	}

	public Taxi() {
		// TODO Auto-generated constructor stub
	}

	public int getTaxiNo() {
		return taxiNo;
	}

	public void setTaxiNo(int taxiNo) {
		this.taxiNo = taxiNo;
	}

	public float getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(float totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public char getCurrentStopPoint() {
		return currentStopPoint;
	}

	public void setCurrentStopPoint(char currentStopPoint) {
		this.currentStopPoint = currentStopPoint;
	}

	public LocalTime getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(LocalTime availableTime) {
		this.availableTime = availableTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
