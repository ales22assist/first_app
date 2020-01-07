import configuration.*;
import dao.ToolKmenovaDAO;
import database_connection_mng.DatabaseConnectionManager;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());

		LOGGER.debug("BEGIN");
		DatabaseConnectionManager.connectDriver();
		ToolKmenovaDAO lopata = new ToolKmenovaDAO();

		try {
			lopata.saveNewToolToKmenova(11, "Lopaata", 10);
			lopata.displayDataKmenova();

		} catch (SQLException exception) {
			System.out.println(exception);
		}
		DatabaseConnectionManager.getManagerInstance().disconnect();
		LOGGER.debug("END");
	}
}