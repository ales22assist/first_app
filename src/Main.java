/*
 * 
 /* JUST FOR TESTING POURPOSSES
 * 
 */
import configuration.*;
import connection_manager.DatabaseConnectionManager;
import dao.ToolDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
	
	public static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {

			PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());
			DatabaseConnectionManager.connectDriver();

			try (Connection connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(),
					ApplicationAccessConf.getUserName(), ApplicationAccessConf.getUserPassword());
					Statement statement = connection.createStatement();
					) {
				
				 ToolDAO.createInventoryTables(connection.createStatement());
				// statement.execute(ToolDAO.saveNewToolToKmenova(15, "PC", 2));
				// statement.execute("(Drop table inventory_kmenova)");
				// statement.execute("(Drop table inventory_zmenova)");
				// statement.execute("(Drop table inventory_protocol)");
				  statement.execute(ToolDAO.createChangeInInventoryZmenova(15, "PC", 11));
				 ToolDAO.makeChange(connection);
				
			} catch (SQLException exc) {
				LOGGER.error(exc);
			}

		//	LOGGER.debug("END");
	}
}