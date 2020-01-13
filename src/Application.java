import configuration.*;
import connection_manager.DatabaseConnectionManager;
import dao.CreateTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());

		LOGGER.debug("BEGIN");
		DatabaseConnectionManager.connectDriver();

		try (Connection connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(),
				ApplicationAccessConf.getUserName(), ApplicationAccessConf.getUserPassword());) {
			
			
			LOGGER.debug("------------Successfully established database connection...------------");
			Statement statement = connection.createStatement();
			
			CreateTables.createInventoryTables(connection.createStatement());
			
			
		//	statement.execute(ToolDAO.updateInventoryKmenova(51, -20, 22));
		//	ToolDAO.makeChange(connection);
			
		//	statement.execute(ToolDAO.createChangeInInventoryZmenova(51, "AUTO", -20));
			//ToolDAO.makeChange(connection);
			
			
		// ToolDAO.saveNewToolToKmenova(51, "AUTO", 22);
		// ToolDAO.createChangeInInventoryZmenova(51, "AUTO", -20);
			//
		//	ToolDAO.displayDataKmenova();

		} catch (Exception e) {
			// TODO: handle exception
		}

		LOGGER.debug("END");
	}
}