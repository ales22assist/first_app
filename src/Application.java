import configuration.*;
import connection_manager.DatabaseConnectionManager;
import dao.CreateTables;
import dao.DisplayTableToConsole;
import dao.ToolDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		
		PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());
		DatabaseConnectionManager.connectDriver();

		LOGGER.debug("BEGIN");
		try (Connection connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(),
				ApplicationAccessConf.getUserName(), ApplicationAccessConf.getUserPassword());
				Statement statement = connection.createStatement();) {
			
			LOGGER.debug("------------Successfully established database connection...------------");
			
			
			 CreateTables.createInventoryTables(connection.createStatement());
			// statement.execute(ToolDAO.saveNewToolToKmenova(1, "PC", 20));
			 // statement.execute(ToolDAO.saveNewToolToKmenova(2, "LOPATA", 15));
			 statement.execute(ToolDAO.createChangeInInventoryZmenova(1, "PC", 15));
			 DisplayTableToConsole.displayDataKmenova();
			 DisplayTableToConsole.displayDataZmenova();
			 DisplayTableToConsole.displayInventoryProtocol();
			 ToolDAO.makeChange(connection);
			
		} catch (Exception exc) {
			LOGGER.error(exc);
		}

		LOGGER.debug("END");
	}
}