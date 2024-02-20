package taxibooking.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JdbcConnection {
	private String url = "jdbc:mysql://localhost:3306/taxibookingapplication";
	private String user = "root";
	private String password = "1234";
	private Connection connection;
	private PreparedStatement preparedStatement;

	protected void establishDbConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected ResultSet executeSelectQuery(String query) throws SQLException {
		preparedStatement = connection.prepareStatement(query);
		return preparedStatement.executeQuery();
	}

	protected int executeUpdateQuery(String query) throws SQLException {
		preparedStatement = connection.prepareStatement(query);
		return preparedStatement.executeUpdate();
	}
}
