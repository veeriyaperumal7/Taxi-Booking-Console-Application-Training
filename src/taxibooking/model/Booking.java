package taxibooking.model;

public class Booking {
	private int bookingId, customerId, fromTime, toTime, taxiId;
	private char fromPoint, toPoint;
	private float amount;

	public Booking(int bookingId, int customerId, int fromTime, int toTime, char fromPoint, char toPoint,
			float amount) {
		this.bookingId = bookingId;
		this.customerId = customerId;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.fromPoint = fromPoint;
		this.toPoint = toPoint;
		this.amount = amount;
	}

	public Booking() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return this.bookingId + "   " + this.customerId + "   " + this.fromPoint + "   " + this.toPoint + "   "
				+ this.fromTime + "   " + this.toTime + "   " + this.amount;

	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getFromTime() {
		return fromTime;
	}

	public void setFromTime(int fromTime) {
		this.fromTime = fromTime;
	}

	public int getToTime() {
		return toTime;
	}

	public void setToTime(int toTime) {
		this.toTime = toTime;
	}

	public char getFromPoint() {
		return fromPoint;
	}

	public void setFromPoint(char fromPoint) {
		this.fromPoint = fromPoint;
	}

	public char getToPoint() {
		return toPoint;
	}

	public void setToPoint(char toPoint) {
		this.toPoint = toPoint;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(int taxiId) {
		this.taxiId = taxiId;
	}

}
