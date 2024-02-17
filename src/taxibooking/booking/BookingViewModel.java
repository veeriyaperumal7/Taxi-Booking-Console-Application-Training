package taxibooking.booking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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

		int fromTime = booking.getFromTime();
		int fromPoint = (int) booking.getFromPoint();

		Collections.sort(tempList, new Comparator<Taxi>() {
			@Override
			public int compare(Taxi o1, Taxi o2) {
				if (o1.getAvailableTime() + Math.abs((int) o1.getCurrentStopPoint() - (int) fromPoint) <= fromTime
						&& o2.getAvailableTime()
								+ Math.abs((int) o2.getCurrentStopPoint() - (int) fromPoint) <= fromTime) {

					if (Math.abs((int) o1.getCurrentStopPoint() - fromPoint) < Math
							.abs((int) o2.getCurrentStopPoint() - fromPoint)) {
						return -1;
					} else if (Math.abs((int) o1.getCurrentStopPoint() - fromPoint) == Math
							.abs((int) o2.getCurrentStopPoint() - fromPoint)) {
						return (int) (o1.getTotalEarnings() - o2.getTotalEarnings());
					} else {
						return 1;
					}
				} else {
					return 0;
				}
			}
		});

		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getAvailableTime()
					+ Math.abs((int) tempList.get(i).getCurrentStopPoint() - (int) fromPoint) <= fromTime) {
				booking.setTaxiId(tempList.get(i).getTaxiNo());
				Repository.getInstance().book(booking);
				bookingView.OnSuccess("Taxi canbe alloted.\nTaxi-" + booking.getTaxiId() + "is allocated...");
				return;
			}
		}
		bookingView.OnFailure();
	}

}
