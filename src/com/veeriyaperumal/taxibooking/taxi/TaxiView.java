package com.veeriyaperumal.taxibooking.taxi;

import java.util.Scanner;

import com.veeriyaperumal.taxibooking.model.Account;

public class TaxiView {

	private TaxiViewModel taxiViewModel;
	private Scanner read = new Scanner(System.in);

	public TaxiView() {
		this.taxiViewModel = new TaxiViewModel(this);
	}


	public void createAccount() {
		Account newAccount = new Account();
		System.out.print("Enter you User Name : ");
		newAccount.setUserName(read.nextLine());
		System.out.print("Enter you User Password : ");
		newAccount.setPassword(read.nextLine());
		newAccount.setRole("TAXI");
		 taxiViewModel.createNewAccount(newAccount);
		
	}

	void onSuccess() {
		System.out.println("Taxi succesfully created...");
	}

	void onFailure() {
		System.out.println("Taxi not created...");
	}

}
