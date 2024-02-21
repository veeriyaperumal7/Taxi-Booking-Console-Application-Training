package com.veeriyaperumal.taxibooking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.veeriyaperumal.taxibooking.booking.BookingView;
import com.veeriyaperumal.taxibooking.onboarview.OnboardView;
import com.veeriyaperumal.taxibooking.report.ReportView;
import com.veeriyaperumal.taxibooking.repository.Repository;
import com.veeriyaperumal.taxibooking.taxi.TaxiView;

public class TaxiBookingApp {

	private BookingView bookingView;
	private ReportView reportView;
	private TaxiView taxiView;
	private OnboardView onboardView = new OnboardView();
	private HashMap<String, ArrayList<String>> masterOptions = new HashMap<>(
			Map.of("USER", new ArrayList<String>(List.of("Booking", "Booking History", "Log Out")), "TAXI",
					new ArrayList<String>(List.of("Travel History", "Log Out")), "ADMIN",
					new ArrayList<String>(List.of("Add Taxi", "Travel Report", "Total Income", "Log Out"))));
	private ArrayList<String> options;
	private Scanner read = new Scanner(System.in);

	public static void main(String[] args) {
		TaxiBookingApp app = new TaxiBookingApp();
		System.out.println("Welcome To Taxi Booking App");
		app.start();

	}

	private void start() {
		initRepository();
		boolean isExit = false;
		do {
			isExit = onboardView.showOnBoardOptions();
			if (!isExit) {
				printFeatures();
			}
		} while (!isExit);

	}

	private void initRepository() {
		try {
			Repository.getInstance().fetchDataFromDb();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

		case "Booking History": {
			if (reportView == null) {
				reportView = new ReportView();
			}
			reportView.getBookingHistory();

			break;
		}

		case "Travel History": {
			if (reportView == null) {
				reportView = new ReportView();
			}
			reportView.getTaxiTravelHistory();

			break;
		}

		case "Add Taxi": {
			if (taxiView == null) {
				taxiView = new TaxiView();
			}
			taxiView.createAccount();

			break;
		}
		case "Travel Report": {
			if (reportView == null) {
				reportView = new ReportView();
			}
			reportView.getTaxiWiseTravelReport();

			break;
		}

		case "Total Income": {
			if (reportView == null) {
				reportView = new ReportView();
			}
			reportView.totalIncomeReport();
			break;
		}

		case "Log Out": {
			onboardView.logOut();
			break;
		}
		}

	}

	private void printFeatures() {
		int selectedOption = 1;
		while (true) {
			options = masterOptions.get(Repository.getInstance().getCurrentUser().getRole());
			System.out.println("Options");
			int serialNo = 1;
			for (String option : options) {
				System.out.println(serialNo++ + option);
			}
			System.out.print("Enter your option : ");
			selectedOption = read.nextInt();
			read.nextLine();
			redirectToSelectedFeature(options.get(selectedOption - 1));
			System.out.println("----------------------------------------------------------------------");
			if (options.get(selectedOption - 1).equals("Log Out")) {
				break;
			}
		}
	}

}
