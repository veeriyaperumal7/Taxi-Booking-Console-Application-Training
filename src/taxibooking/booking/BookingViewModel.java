package taxibooking.booking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import taxibooking.model.Booking;
import taxibooking.model.Taxi;
import taxibooking.model.TaxiStatus;
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
				bookingView.OnSuccess("Taxi canbe alloted.\nTaxi-"+booking.getTaxiId()+"is allocated...");
				return;
			}
		}
		bookingView.OnFailure();

	}

//	private boolean checkTaxiAvailability(char fromPoint, int fromTime) {
//		HashMap<Character, ArrayList<TaxiStatus>> PickupPoints = Repository.getInstance().getPickupPoints();
//		ArrayList<TaxiStatus> leftSidePoints, rightSidePoints;
//		for (int i = 0; i < PickupPoints.size(); i++) {
//			if (i == 0) {
//				leftSidePoints = PickupPoints.get(fromPoint);
//				if (leftSidePoints != null) {
//					int index = getTaxiIndex(leftSidePoints, fromTime);
//				}
//			} else {
//				int leftIndex = -1, rightIndex = -1;
//				leftSidePoints = PickupPoints.get((char) (fromPoint + i));
//				rightSidePoints = PickupPoints.get((char) (fromPoint + i));
//				if (leftSidePoints != null && rightSidePoints != null) {
//					leftIndex = getTaxiIndex(leftSidePoints, fromTime);
//					rightIndex = getTaxiIndex(rightSidePoints, fromTime);
//					if (leftIndex != -1 && rightIndex != -1) {
//						TaxiStatus selectedTaxi;
//						if (Repository.getInstance().getTaxies().get(leftSidePoints.get(leftIndex).getTaxiId())
//								.getTotalEarnings() > Repository.getInstance().getTaxies()
//										.get(rightSidePoints.get(rightIndex).getTaxiId()).getTotalEarnings()) {
//							selectedTaxi = leftSidePoints.get(leftIndex);
//							changePickingTaxiStatus()
//						} else {
//							selectedTaxi = rightSidePoints.get(rightIndex);
//						}
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	private int getTaxiIndex(ArrayList<TaxiStatus> pickUpPoints, int fromTime) {
//		if (pickUpPoints != null) {
//			Collections.sort(pickUpPoints, new Comparator<TaxiStatus>() {
//
//				@Override
//				public int compare(TaxiStatus o1, TaxiStatus o2) {
//					return (int) (Repository.getInstance().getTaxies().get(o1.getTaxiId()).getTotalEarnings()
//							- Repository.getInstance().getTaxies().get(o2.getTaxiId()).getTotalEarnings());
//				}
//			});
//		}
//		for (int i = 0; i < pickUpPoints.size(); i++) {
//			if (fromTime >= pickUpPoints.get(i).getFreeTime()) {
//				return i;
//			}
//		}
//		return -1;
//	}

}
