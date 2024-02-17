package taxibooking.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import taxibooking.model.Booking;
import taxibooking.model.Taxi;
import taxibooking.repository.Repository;

public class ReportViewModel {

	private ReportView reportView;

	protected ReportViewModel(ReportView reportView) {
		this.reportView = reportView;
	}

	protected List<Integer> getTaxiId() {
		HashMap<Integer, Taxi> taxies = Repository.getInstance().getTaxies();
		Collection<Integer> taxiCollection = taxies.keySet();
		return new ArrayList<>(taxiCollection);
	}

	protected Taxi getTaxi(int no) {
		return Repository.getInstance().getTaxies().get(no);
	}

	protected Booking getBooking(int bookingNo) {
		return Repository.getInstance().getBookings().get(bookingNo);
	}

}
