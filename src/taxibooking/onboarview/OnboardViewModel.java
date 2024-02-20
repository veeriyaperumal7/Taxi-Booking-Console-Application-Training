package taxibooking.onboarview;

import java.sql.SQLException;

import taxibooking.model.Account;
import taxibooking.repository.Repository;

public class OnboardViewModel {

	private OnboardView onboardView;

	OnboardViewModel(OnboardView onboardView) {
		this.onboardView = onboardView;
	}

	boolean login(Account loginAccount) {
		try {
			return Repository.getInstance().login(loginAccount);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	 boolean createNewAccount(Account newAccount) {
		try {
			return Repository.getInstance().createNewAccount(newAccount);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	 void logOut() {
		Repository.getInstance().logOut();
	}

}
