package com.veeriyaperumal.taxibooking.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.veeriyaperumal.taxibooking.model.Booking;
import com.veeriyaperumal.taxibooking.model.Taxi;

public class ReportView {

	private ReportViewModel reportViewModel;

	public ReportView() {
		this.reportViewModel = new ReportViewModel(this);
	}

	public void getBookingHistory() {
		try {
			List<Booking> bookingHistory = reportViewModel.getCurrentUserBookingHistory();
			if (bookingHistory == null || bookingHistory.size() < 1) {
				System.out.println("There is no data...");
			} else {
				System.out.println("BookingID   CustomerID    From    To      Pickuptime      DropTime     Amount");
				for (Booking book : bookingHistory) {
					System.out.println(book.toString());
				}
			}
		} catch (Exception e) {
			System.out.println("There is no data...");
			e.printStackTrace();
		}
	}

	public void getTaxiTravelHistory() {
		float earnings;
		try {
			earnings = reportViewModel.getTaxiEarnings();
		} catch (SQLException e) {
			e.printStackTrace();
			earnings = 0;
		}
		System.out.println("Total Earnings : " + earnings);
		List<Booking> travelHistory;
		try {
			travelHistory = reportViewModel.getTaxiTravelHistory();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("There is no data...");
			return;
		}
		if (travelHistory == null || travelHistory.size() < 1) {
			System.out.println("There is no data...");
		} else {
			System.out.println("BookingID   CustomerID    From    To      Pickuptime      DropTime     Amount");
			for (Booking book : travelHistory) {
				System.out.println(book.toString());
			}
		}
	}

	public void getTaxiWiseTravelReport() {
		HashMap<Taxi, ArrayList<Booking>> map;
		try {
			map = reportViewModel.getTaxiWiseTravelReport();
			if (map == null || map.size() < 1) {
				System.out.println("There is no data found...");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("There is no data found...");
			return;
		}

		for (Entry<Taxi, ArrayList<Booking>> entry : map.entrySet()) {
			System.out.println("Taxi-" + entry.getKey().getTaxiNo());
			System.out.println("Total Earnings : " + entry.getKey().getTotalEarnings());
			System.out.println("BookingID   CustomerID    From    To      Pickuptime      DropTime     Amount");
			for (Booking booking : entry.getValue()) {
				System.out.println(booking.toString());
			}
			System.out.println("\n");
		}

	}

	public void totalIncomeReport() {
		HashMap<Integer, Taxi> taxies = reportViewModel.getTaxies();
		if (taxies == null || taxies.size() < 1) {
			System.out.println("There is no data found...");
			return;
		}
		float totalAmount = 0.0f;
		System.out.println("Income data : ");
		for (Entry<Integer, Taxi> entry : taxies.entrySet()) {
			System.out.println("Taxi - " + entry.getValue().getTaxiNo() + "\nEarning : "
					+ entry.getValue().getTotalEarnings() + "\n");
			totalAmount += entry.getValue().getTotalEarnings();
		}
		System.out.println("Total Income : " + totalAmount);
	}

}
