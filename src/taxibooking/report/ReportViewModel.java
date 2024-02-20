package taxibooking.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import taxibooking.model.Booking;
import taxibooking.model.Taxi;
import taxibooking.repository.Repository;

public class ReportViewModel {

	private ReportView reportView;

	ReportViewModel(ReportView reportView) {
		this.reportView = reportView;
	}

	List<Integer> getTaxiId() {
		HashMap<Integer, Taxi> taxies = Repository.getInstance().getTaxies();
		Collection<Integer> taxiCollection = taxies.keySet();
		return new ArrayList<>(taxiCollection);
	}

	Taxi getTaxi(int no) {
		return Repository.getInstance().getTaxies().get(no);
	}

	Booking getBooking(int bookingNo) {
		return Repository.getInstance().getBookings().get(bookingNo);
	}

	List<Booking> getCurrentUserBookingHistory() {
		try {
			return Repository.getInstance().getCurrentUserBookingHistory();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	float getTaxiEarnings() throws SQLException {
		return Repository.getInstance().getTaxiEarnings();
	}

	HashMap<Taxi, ArrayList<Booking>> getTaxiWiseTravelReport() throws SQLException {
		return Repository.getInstance().getTaxiWiseTravelReport();
	}

	HashMap<Integer, Taxi> getTaxies() {
		return Repository.getInstance().getTaxies();
	}

	 List<Booking> getTaxiTravelHistory() throws SQLException {
		return Repository.getInstance().getTaxiTravelHistory();
	}

}
