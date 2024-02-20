package taxibooking.booking;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import taxibooking.model.Booking;

public class BookingView {

	private BookingViewModel bookingViewModel;
	private Scanner read = new Scanner(System.in);

	public BookingView() {
		this.bookingViewModel = new BookingViewModel(this);
	}

	public void startBooking() {
		Booking booking = getBookingData();
		bookingViewModel.bookTaxi(booking);
	}

	protected void OnSuccess(String message) {
		System.out.println(message);
	}

	protected void OnFailure() {
		System.out.println("Taxi not allocated...");
	}

	private Booking getBookingData() {
		Booking booking = new Booking();

		int customerId;
		String pickUpTime;
		char pickupPoint, dropPoint;
		customerId = bookingViewModel.getCurrentUser().getId();
		System.out.print("Pickup Point :");
		pickupPoint = read.nextLine().charAt(0);
		System.out.print("Drop Point   :");
		dropPoint = read.nextLine().charAt(0);
		System.out.print("(Note :Enter Raliway Time  - 13.01 )\nPickup Time  :");
		pickUpTime = read.nextLine();
		booking.setCustomerId(customerId);
		booking.setFromPoint(pickupPoint);
		booking.setToPoint(dropPoint);
		booking.setFromTime(LocalTime.of(Integer.parseInt(pickUpTime.substring(0, 2)),
				Integer.parseInt(pickUpTime.substring(3, 5))));

		return booking;
	}


}
