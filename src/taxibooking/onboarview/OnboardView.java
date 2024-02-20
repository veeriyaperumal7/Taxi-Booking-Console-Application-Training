package taxibooking.onboarview;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import taxibooking.model.Account;

public class OnboardView {
	private OnboardViewModel onboardViewModel;
	private ArrayList<String> options = new ArrayList<>(List.of("Login", "User Account creation", "Exit"));
	private Scanner read = new Scanner(System.in);

	public OnboardView() {
		onboardViewModel = new OnboardViewModel(this);
	}

	public boolean showOnBoardOptions() {
		int selectedOption = -1;
		boolean flag = false;
		do {
			printFeatures();
			System.out.print("Enter your option : ");
			selectedOption = read.nextInt();
			read.nextLine();
			flag = redirectToSelectedFeature(options.get(selectedOption - 1));
			System.out.println("----------------------------------------------------------------------");
		} while (!flag);
		if (options.get(selectedOption - 1).equals("Exit")) {
			return true;
		} else {
			return false;
		}

	}

	private boolean redirectToSelectedFeature(String option) {
		switch (option) {
		case "Login": {
			return login();
		}
		case "User Account creation": {
			return createNewAccount();
		}
		case "Exit": {
			System.out.println("Thanks for using app...");
			return true;
		}
		}
		return false;

	}

	private boolean createNewAccount() {
		Account newAccount = new Account();
		System.out.print("Enter you User Name : ");
		newAccount.setUserName(read.nextLine());
		System.out.print("Enter you User Password : ");
		newAccount.setPassword(read.nextLine());
		newAccount.setRole("USER");
		if (onboardViewModel.createNewAccount(newAccount)) {
			System.out.println("New account created successfully...");
			return true;
		} else {
			System.out.println("New account not created...");
			return false;
		}
	}

	private boolean login() {
		Account loginAccount = new Account();
		System.out.print("Enter you User Name : ");
		loginAccount.setUserName(read.nextLine());
		System.out.print("Enter you User Password : ");
		loginAccount.setPassword(read.nextLine());
		if (onboardViewModel.login(loginAccount)) {
			System.out.println("Account login successfully...");
			return true;
		} else {
			System.out.println("Account login failure.Check your user id and password...");
			return false;
		}
	}

	private void printFeatures() {
		System.out.println("Options");
		int serialNo = 1;
		for (String option : options) {
			System.out.println(serialNo++ + option);
		}

	}

	public void logOut() {
		onboardViewModel.logOut();
	}

}
