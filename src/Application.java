import configuration.*;
import connection_manager.DatabaseConnectionManager;
import dao.ToolDAO;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());

		LOGGER.debug("BEGIN");
		DatabaseConnectionManager.connectDriver();		

		try (Connection connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(), 
																ApplicationAccessConf.getUserName(),
																ApplicationAccessConf.getUserPassword());){
			LOGGER.debug("------------Successfully established database connection...------------");
			
			ToolDAO.createInventoryTables(connection.createStatement());
			ToolDAO.displayDataKmenova();
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		LOGGER.debug("END");
	}
}