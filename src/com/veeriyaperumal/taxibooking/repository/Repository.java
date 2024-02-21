package com.veeriyaperumal.taxibooking.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.veeriyaperumal.taxibooking.databaseconnection.JdbcConnection;
import com.veeriyaperumal.taxibooking.model.Account;
import com.veeriyaperumal.taxibooking.model.Booking;
import com.veeriyaperumal.taxibooking.model.Taxi;

public class Repository extends JdbcConnection {
	private static Repository repository;
	private HashMap<Integer, Taxi> taxies;
	private HashMap<Integer, Booking> bookings;
	private Account currentUser = null;

	public Account getCurrentUser() {
		return currentUser;
	}

	private String query;
	private ResultSet result;

	private Repository() {
		this.taxies = new HashMap<>();
		this.bookings = new HashMap<>();
		establishDbConnection();
	}

	public static Repository getInstance() {
		if (repository == null) {
			repository = new Repository();
		}
		return repository;
	}

	public void fetchDataFromDb() throws SQLException {
		fetchTaxiData();
	}

	private void fetchTaxiData() throws SQLException {
		query = "select * from taxi_data";
		result = executeSelectQuery(query);
		while (result.next()) {
			Taxi taxi = new Taxi();
			taxi.setTaxiNo(result.getInt("taxi_id"));
			taxi.setTotalEarnings(result.getFloat("earnings"));
			Time sqlTime = result.getTime("available_time");
			taxi.setAvailableTime(sqlTime.toLocalTime());
			taxi.setCurrentStopPoint(result.getString("available_point").charAt(0));
			taxi.setUserId(result.getInt("account_id"));
			taxies.put(taxi.getTaxiNo(), taxi);
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

	public boolean book(Booking booking) throws SQLException {
		int distance = Math.abs(((int) booking.getFromPoint()) - ((int) booking.getToPoint())) * 15;
		booking.setBookingId(getNewBookingId());
		booking.setToTime(booking.getFromTime().plusHours(distance / 15));
		booking.setAmount(100 + (distance - 5) * 10);
		Taxi taxi = taxies.get(booking.getTaxiId());
		taxi.setAvailableTime(booking.getToTime());
		taxi.setCurrentStopPoint(booking.getToPoint());
		taxi.setTotalEarnings(taxi.getTotalEarnings() + booking.getAmount());
		query = "insert into booking_data value(" + booking.getBookingId() + " ," + booking.getTaxiId() + ","
				+ booking.getCustomerId() + ",'" + booking.getFromTime() + "','" + booking.getToTime() + "','"
				+ booking.getFromPoint() + "','" + booking.getToPoint() + "'," + booking.getAmount() + ")";
		if (executeUpdateQuery(query) > 0) {
			query = "update taxi_data set earnings = earnings + " + booking.getAmount() + ",available_time = '"
					+ booking.getToTime() + "',available_point='" + booking.getToPoint() + "'  where taxi_id = "
					+ booking.getTaxiId();
			return executeUpdateQuery(query) > 0;
		} else {
			return false;
		}

	}

	private int getNewBookingId() throws SQLException {
		query = "Select max(bookingId) as max from booking_data";
		try {
			result = executeSelectQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result.next()) {
			int id = result.getInt("max");
			return id + 1;
		}
		return 1;
	}

	public boolean login(Account loginAccount) throws SQLException {
		query = "select * from accounts where user_name='" + loginAccount.getUserName() + "'" + " and user_password='"
				+ loginAccount.getPassword() + "'";
		result = executeSelectQuery(query);
		if (result.next()) {
			currentUser = new Account();
			currentUser.setId(result.getInt("id"));
			currentUser.setUserName(result.getString("user_name"));
			currentUser.setRole(result.getString("role"));
			return true;
		}
		return false;
	}

	public boolean createNewAccount(Account newAccount) throws SQLException {
		newAccount.setId(getNewAccountId());
		query = "insert into accounts  (id, user_name, user_password, role) VALUES  (" + newAccount.getId() + ",'"
				+ newAccount.getUserName() + "','" + newAccount.getPassword() + "','" + newAccount.getRole() + "')";
		if (executeUpdateQuery(query) > 0) {
			currentUser = new Account();
			currentUser.setId(newAccount.getId());
			currentUser.setUserName(newAccount.getUserName());
			currentUser.setRole(newAccount.getRole());
			return true;
		}
		return false;
	}

	private int getNewAccountId() throws SQLException {
		query = "Select max(id) as max from accounts";
		result = executeSelectQuery(query);
		if (result.next()) {
			int id = result.getInt("max");
			return id + 1;
		}
		return 1;
	}

	public void logOut() {
		currentUser = null;
	}

	public List<Booking> getCurrentUserBookingHistory() throws SQLException {
		List<Booking> bookingHistory = new ArrayList<>();
		query = "Select * from booking_data where customerId = " + currentUser.getId();
		result = executeSelectQuery(query);
		while (result.next()) {
			int bookingId = result.getInt("bookingId");
			int customerId = result.getInt("customerId");
			int taxiId = result.getInt("taxiId");
			LocalTime fromTime = result.getTime("fromTime").toLocalTime();
			LocalTime toTime = result.getTime("toTime").toLocalTime();
			char fromPoint = result.getString("fromPoint").charAt(0);
			char toPoint = result.getString("toPoint").charAt(0);
			float amount = result.getFloat("amount");
			Booking booking = new Booking(bookingId, customerId, fromTime, toTime, fromPoint, toPoint, amount);
			booking.setTaxiId(taxiId);
			bookingHistory.add(booking);
		}
		return bookingHistory;
	}

	public float getTaxiEarnings() throws SQLException {
		query = "Select sum(amount) as earnings from booking_data where account_id = " + currentUser.getId();
		return executeSelectQuery(query).getFloat(0);
	}

	public HashMap<Taxi, ArrayList<Booking>> getTaxiWiseTravelReport() throws SQLException {
		HashMap<Taxi, ArrayList<Booking>> map = new HashMap<Taxi, ArrayList<Booking>>();
		Collection<Taxi> tempList = taxies.values();
		List<Taxi> taxiList = new ArrayList<Taxi>(tempList);
		for (Taxi taxi : taxiList) {
			ArrayList<Booking> bookings = new ArrayList();
			query = "Select * from booking_data where taxiId = " + taxi.getTaxiNo();
			result = executeSelectQuery(query);
			while (result.next()) {
				int bookingId = result.getInt("bookingId");
				int customerId = result.getInt("customerId");
				int taxiId = result.getInt("taxiId");
				LocalTime fromTime = result.getTime("fromTime").toLocalTime();
				LocalTime toTime = result.getTime("toTime").toLocalTime();
				char fromPoint = result.getString("fromPoint").charAt(0);
				char toPoint = result.getString("toPoint").charAt(0);
				float amount = result.getFloat("amount");
				Booking booking = new Booking(bookingId, customerId, fromTime, toTime, fromPoint, toPoint, amount);
				booking.setTaxiId(taxiId);
				bookings.add(booking);
			}
			map.put(taxi, bookings);
		}

		return map;
	}

	private int getNewTaxiId() throws SQLException {
		query = "Select max(taxi_id) as max from taxi_data";
		result = executeSelectQuery(query);
		if (result.next()) {
			int id = result.getInt("max");
			return id + 1;
		}
		return 1;
	}

	public boolean createNewTaxiAccount(Account newAccount) throws SQLException {
		newAccount.setId(getNewAccountId());
		query = "insert into accounts  (id, user_name, user_password, role) VALUES  (" + newAccount.getId() + ",'"
				+ newAccount.getUserName() + "','" + newAccount.getPassword() + "','" + newAccount.getRole() + "')";
		executeUpdateQuery(query);
		int id = getNewTaxiId();
		Taxi newTaxi = new Taxi(id);
		newTaxi.setUserId(newAccount.getId());
		taxies.put(id, newTaxi);
		query = "insert into taxi_data (taxi_id,earnings,available_time,available_point,account_id) values ("
				+ newTaxi.getTaxiNo() + "," + newTaxi.getTotalEarnings() + ",'" + newTaxi.getAvailableTime() + "','"
				+ newTaxi.getCurrentStopPoint() + "'," + newTaxi.getUserId() + ")";
		return executeUpdateQuery(query) > 0;

	}

	public List<Booking> getTaxiTravelHistory() throws SQLException {
		List<Booking> bookingHistory = new ArrayList<>();
		query = "Select * from booking_data where account_id = " + currentUser.getId();
		result = executeSelectQuery(query);
		while (result.next()) {
			int bookingId = result.getInt("bookingId");
			int customerId = result.getInt("customerId");
			int taxiId = result.getInt("taxiId");
			LocalTime fromTime = result.getTime("fromTime").toLocalTime();
			LocalTime toTime = result.getTime("toTime").toLocalTime();
			char fromPoint = result.getString("fromPoint").charAt(0);
			char toPoint = result.getString("toPoint").charAt(0);
			float amount = result.getFloat("amount");
			Booking booking = new Booking(bookingId, customerId, fromTime, toTime, fromPoint, toPoint, amount);
			booking.setTaxiId(taxiId);
			bookingHistory.add(booking);
		}
		return bookingHistory;
	}
}
