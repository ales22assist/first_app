package configuration;

import property_file_units.PropertyFileUnits;

import org.apache.log4j.Logger;

import exception.*;

public class ApplicationAccessConf {

	public static final Logger LOGGER = Logger.getLogger(ApplicationAccessConf.class);

	private static final String LOG_PROPERTY_FILE = "C:\\dev\\logger\\log.properties";
	static String propertyStringValue;

	PropertyFileUnits propertyFileUnits = PropertyFileUnits.getInstance();

	public static String getDriverClass() {

		try {
			return PropertyFileUnits.getInstance().getProperty("DRIVER_CLASS");
		} catch (PropertyFileException e) {
			LOGGER.debug("COULDNT FIND A DRIVER_CLASS");
			return "";
		}
	}

	public static String getUrl() {
		try {
			return PropertyFileUnits.getInstance().getProperty("URL");
		} catch (PropertyFileException e) {
			LOGGER.debug("COULDNT FIND A CORRECT URL");
			return "";
		}
	}

	public static String getUserName() {
		try {
			return PropertyFileUnits.getInstance().getProperty("USER_NAME");
		} catch (PropertyFileException e) {
			LOGGER.debug("USER_NAME DIDNT MATCH");
			return "";
		}
	}

	public static String getUserPassword() {
		try {
			return PropertyFileUnits.getInstance().getProperty("USER_PASSWORD");
		} catch (PropertyFileException e) {
			LOGGER.debug("USER_PASSWORD DIDNT MATCH");
			return "";
		}
	}

	public static String getTabName() {
		try {
			return PropertyFileUnits.getInstance().getProperty("TAB_NAME");
		} catch (PropertyFileException e) {
			LOGGER.debug("TAB_NAME NOT FOUND");
			return "";
		}
	}

	public static String getLogPropertyFile() {
		return LOG_PROPERTY_FILE;
	}
}