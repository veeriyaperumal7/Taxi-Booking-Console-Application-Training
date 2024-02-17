package taxibooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import taxibooking.booking.BookingView;
import taxibooking.report.ReportView;
import taxibooking.repository.Repository;

public class TaxiBookingApp {

	private BookingView bookingView;
	private ReportView reportView;
	private ArrayList<String> options = new ArrayList<>(List.of("Booking", "Display the taxi details", "Exit"));
	private Scanner read = new Scanner(System.in);

	public static void main(String[] args) {
		TaxiBookingApp app = new TaxiBookingApp();
		System.out.println("Welcome To Taxi Booking App");
		app.start();

	}

	private void start() {
		initRepository();
		int selectedOption = -1;
		do {
			printFeatures();
			System.out.print("Enter your option : ");
			selectedOption = read.nextInt();
			read.nextLine();
			redirectToSelectedFeature(options.get(selectedOption - 1));
			System.out.println("----------------------------------------------------------------------");
		} while (selectedOption != options.size());

	}

	private void initRepository() {
		int taxiCount = 0, pickPointsCount = 0;
		System.out.print("Enter how many taxis  : ");
		taxiCount = read.nextInt();
		System.out.print("Enter how many pickup points  : ");
		pickPointsCount = read.nextInt();
		read.nextLine();
		Repository.getInstance().initRepository(taxiCount, pickPointsCount);
	}

	private void redirectToSelectedFeature(String option) {
		switch (option) {
		case "Booking": {
			if (bookingView == null) {
				bookingView = new BookingView();
			}
			bookingView.startBooking();

			break;
		}
		case "Display the taxi details": {
			if (reportView == null) {
				reportView = new ReportView();
			}
			reportView.showDetailedTaxiReport();
			break;
		}
		case "Exit": {
			System.out.println("Thanks for using app...");
			break;
		}
		}

	}

	private void printFeatures() {
		System.out.println("Options");
		int serialNo = 1;
		for (String option : options) {
			System.out.println(serialNo++ + option);
		}

	}

}
