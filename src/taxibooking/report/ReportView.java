package taxibooking.report;

import java.util.List;

import taxibooking.model.Taxi;

public class ReportView {

	private ReportViewModel reportViewModel;

	public ReportView() {
		this.reportViewModel = new ReportViewModel(this);
	}

	public void showDetailedTaxiReport() {
		List<Integer> taxiNumbers = reportViewModel.getTaxiId();
		for (int taxiNo : taxiNumbers) {
			Taxi taxi = reportViewModel.getTaxi(taxiNo);
			if (taxi.getBookingHistory().size() > 0) {
				System.out.println("Taxi-" + taxiNo);
				System.out.println("Total Earnings : " + taxi.getTotalEarnings());
				System.out.println("BookingID   CustomerID    From    To      Pickuptime      DropTime     Amount");
				for (int bookingId : taxi.getBookingHistory()) {
					System.out.println(reportViewModel.getBooking(bookingId).toString());
				}
				System.out.println("\n");
			}
		}
	}

}
