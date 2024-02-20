package taxibooking.taxi;

import java.sql.SQLException;

import taxibooking.model.Account;
import taxibooking.repository.Repository;

public class TaxiViewModel {

	private TaxiView taxiView;

	TaxiViewModel(TaxiView taxiView) {
		this.taxiView = taxiView;
	}

	void createNewAccount(Account newAccount) {
		try {
			if (Repository.getInstance().createNewTaxiAccount(newAccount)) {
				taxiView.onSuccess();
			} else {
				taxiView.onFailure();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			taxiView.onFailure();
		}
	}

}
