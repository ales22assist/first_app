package connection_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import configuration.ApplicationAccessConf;

public class DatabaseConnectionManager {

	private Connection connection;
	private static DatabaseConnectionManager connectionInstance = new DatabaseConnectionManager();

	private DatabaseConnectionManager() {
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

	public static DatabaseConnectionManager getManagerInstance() {
		return connectionInstance;
	}

	public Connection getConnectionObject() {
		return connection;
	}

	public void connect() {
		try {
			connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(),
					ApplicationAccessConf.getUserName(), ApplicationAccessConf.getUserPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Successfully disconnected from database...");
	}
}