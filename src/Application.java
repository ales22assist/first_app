import configuration.*;
import database.DatabaseConnectionManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		PropertyConfigurator.configure(ApplicationAccessConf.getLogPropertyFile());

		LOGGER.debug("BEGIN");

		DatabaseConnectionManager.connectDriver();

		try {
			DatabaseConnectionManager.getManagerInstance().connect();
		} catch (Exception exception) {
			System.out.println(exception);
		}
		LOGGER.debug("END");
	}
}