package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import configuration.ApplicationAccessConf;

public class DatabaseConnectionManager {

	private Connection connection;
	private static DatabaseConnectionManager connectionInstance = new DatabaseConnectionManager();

	private DatabaseConnectionManager() {
	}

	public static DatabaseConnectionManager getManagerInstance() {
		return connectionInstance;
	}

	public void connect() throws SQLException {
		connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(), ApplicationAccessConf.getUserName(),
				ApplicationAccessConf.getUserPassword());
		System.out.println("Successfully established database connection...");
	}

	public Connection getConnectionObject() {
		return connection;
	}

	public void disconnect() throws SQLException {
		connection.close();
		System.out.println("Successfully disconnected from database...");
	}

	public static void connectDriver() {

		try {
			Class.forName(ApplicationAccessConf.getDriverClass());

		} catch (ClassNotFoundException exception) {
			System.out.println("Driver not loaded...");
			System.out.println(exception);
			return;
		}
	}
}
