import configuration.*;
import connection_manager.DatabaseConnectionManager;
import dao.ToolDAO;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

	public static void main(String[] args) {

			PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());

			DatabaseConnectionManager.connectDriver();

			try (Connection connection = DriverManager.getConnection(ApplicationAccessConf.getUrl(),
					ApplicationAccessConf.getUserName(), ApplicationAccessConf.getUserPassword());) {
				//LOGGER.debug("------------Successfully established database connection...------------");

				//	ToolDAO.saveNewToolToKmenova(2, "Lopata", 10);
					ToolDAO.displayDataKmenova();

			} catch (Exception e) {
				// TODO: handle exception
			}

		//	LOGGER.debug("END");
	}
}
