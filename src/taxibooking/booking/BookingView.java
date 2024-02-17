package taxibooking.booking;

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
		int customerId, pickUpTime;
		char pickupPoint, dropPoint;
		System.out.print("Customer ID  :");
		customerId = read.nextInt();
		read.nextLine();
		System.out.print("Pickup Point :");
		pickupPoint = read.nextLine().charAt(0);
		System.out.print("Drop Point   :");
		dropPoint = read.nextLine().charAt(0);
		System.out.print("Pickup Time  :");
		pickUpTime = read.nextInt();
		read.nextLine();
		booking.setCustomerId(customerId);
		booking.setFromPoint(pickupPoint);
		booking.setToPoint(dropPoint);
		booking.setFromTime(pickUpTime);

		return booking;
	}

}
