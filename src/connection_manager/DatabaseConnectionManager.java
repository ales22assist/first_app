package connection_manager;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import configuration.ApplicationAccessConf;

public class DatabaseConnectionManager {
	
	public static final Logger LOGGER = Logger.getLogger(DatabaseConnectionManager.class);

	private Connection connection;
	private static DatabaseConnectionManager connectionInstance = new DatabaseConnectionManager();

	private DatabaseConnectionManager() {
	}

	public static void connectDriver() {
		try {
			Class.forName(ApplicationAccessConf.getDriverClass());

		} catch (ClassNotFoundException exception) {
			LOGGER.debug("DRIVER NOT LOADED");
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
		} catch (Exception e) {
			LOGGER.debug("CONNECTION FAILED");
		}
	}

	public void disconnect() {
		try {
			connection.close();
		} catch (Exception e) {
			LOGGER.debug("COULDNT DISCONNECT PROPERLY");
		}
	}
}