package taxibooking.model;

import java.util.ArrayList;

public class Taxi {

	private int taxiNo;
	private float totalEarnings;
	private ArrayList<Integer> bookingHistory;
	private char currentStopPoint;
	private int availableTime;

	public Taxi(int taxiNo) {
		this.taxiNo = taxiNo;
		this.totalEarnings = 0.0f;
		this.bookingHistory = new ArrayList<>();
		this.availableTime = 0;
		this.currentStopPoint = 'A';
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

	public ArrayList<Integer> getBookingHistory() {
		return bookingHistory;
	}

	public void setBookingHistory(ArrayList<Integer> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}

	public char getCurrentStopPoint() {
		return currentStopPoint;
	}

	public void setCurrentStopPoint(char currentStopPoint) {
		this.currentStopPoint = currentStopPoint;
	}

	public int getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(int availableTime) {
		this.availableTime = availableTime;
	}

}
