package taxibooking.model;

public class TaxiStatus {
	private int taxiId, freeTime;

	public TaxiStatus(int taxiId, int freeTime) {
		this.taxiId = taxiId;
		this.freeTime = freeTime;
		
	}

	public int getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(int taxiId) {
		this.taxiId = taxiId;
	}

	public int getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(int freeTime) {
		this.freeTime = freeTime;
	}
}
