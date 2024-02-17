package taxibooking.repository;

import java.util.HashMap;

import taxibooking.model.Booking;
import taxibooking.model.Taxi;

public class Repository {
	private static Repository repository;
	private HashMap<Integer, Taxi> taxies;
	private HashMap<Integer, Booking> bookings;

	private Repository() {
		this.taxies = new HashMap<>();
		this.bookings = new HashMap<>();
	}

	public static Repository getInstance() {
		if (repository == null) {
			repository = new Repository();
		}
		return repository;
	}

	public void initRepository(int taxiCount, int pickPointsCount) {
		for (int taxiId = 1; taxiId <= taxiCount; taxiId++) {
			Taxi taxi = new Taxi(taxiId);
			taxies.put(taxiId, taxi);
		}
	}

	public HashMap<Integer, Taxi> getTaxies() {
		return taxies;
	}

	public void setTaxies(HashMap<Integer, Taxi> taxies) {
		this.taxies = taxies;
	}

	public HashMap<Integer, Booking> getBookings() {
		return bookings;
	}

	public void setBookings(HashMap<Integer, Booking> bookings) {
		this.bookings = bookings;
	}

	public void book(Booking booking) {
		booking.setBookingId(getBookings().size() + 1);
		int distance = Math.abs(((int) booking.getFromPoint()) - ((int) booking.getToPoint())) * 15;
		booking.setToTime(booking.getFromTime() + distance / 15);
		booking.setAmount(100 + (distance - 5) * 10);
		Taxi taxi = taxies.get(booking.getTaxiId());
		taxi.setAvailableTime(booking.getToTime());
		taxi.setCurrentStopPoint(booking.getToPoint());
		taxi.setTotalEarnings(taxi.getTotalEarnings() + booking.getAmount());
		taxi.getBookingHistory().add(booking.getBookingId());
		bookings.put(booking.getBookingId(), booking);
	}

}
