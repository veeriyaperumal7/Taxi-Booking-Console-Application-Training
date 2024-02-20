package taxibooking.booking;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import taxibooking.model.Account;
import taxibooking.model.Booking;
import taxibooking.model.Taxi;
import taxibooking.repository.Repository;

public class BookingViewModel {

	private BookingView bookingView;

	public BookingViewModel(BookingView bookingView) {
		this.bookingView = bookingView;
	}

	protected void bookTaxi(Booking booking) {
		HashMap<Integer, Taxi> taxies = Repository.getInstance().getTaxies();
		Collection<Taxi> taxiCollection = taxies.values();
		List<Taxi> tempList = new ArrayList<>(taxiCollection);

		LocalTime fromTime = booking.getFromTime();
		int fromPoint = (int) booking.getFromPoint();

		Collections.sort(tempList, new Comparator<Taxi>() {
			@Override
			public int compare(Taxi o1, Taxi o2) {
				LocalTime fromTime = booking.getFromTime();
				int fromPoint = (int) booking.getFromPoint();

				int distance1 = Math.abs((int) o1.getCurrentStopPoint() - fromPoint);
				int distance2 = Math.abs((int) o2.getCurrentStopPoint() - fromPoint);

				if (o1.getAvailableTime().plusMinutes(distance1).isBefore(fromTime)
						&& o2.getAvailableTime().plusMinutes(distance2).isBefore(fromTime)) {

					if (distance1 < distance2) {
						return -1;
					} else if (distance1 == distance2) {
						return Double.compare(o1.getTotalEarnings(), o2.getTotalEarnings());
					} else {
						return 1;
					}
				} else {
					return 0;
				}
			}
		});

		for (Taxi taxi : tempList) {
			int distance = Math.abs((int) taxi.getCurrentStopPoint() - fromPoint);
			if (taxi.getAvailableTime().plusMinutes(distance).isBefore(fromTime)) {
				booking.setTaxiId(taxi.getTaxiNo());
				try {
					if (Repository.getInstance().book(booking)) {
						bookingView
								.OnSuccess("Taxi can be allotted.\nTaxi-" + booking.getTaxiId() + " is allocated...");

					} else {
						bookingView.OnFailure();
					}
					return;
				} catch (SQLException e) {
					e.printStackTrace();
					bookingView.OnFailure();
					return;
				}
			}
		}
		bookingView.OnFailure();
	}

	Account getCurrentUser() {
		return Repository.getInstance().getCurrentUser();
	}

}
