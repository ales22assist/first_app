package db_connection;

import configuration.ApplicationConf;

public class DatabaseConnection {
	
	public static void connectDriver() {
		
	try {
		Class.forName(ApplicationConf.getDriverClass());
		
	} catch (ClassNotFoundException exception) {
		System.out.println("failed to load driver");
		System.out.println(exception);
		return;
		}
	}
}