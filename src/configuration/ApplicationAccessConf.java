package configuration;

import property_file_units.PropertyFileUnits;

import exception.*;

public class ApplicationAccessConf {

	private static final String LOG_PROPERTY_FILE = "C:\\dev\\logger\\log.properties";
	static String propertyStringValue;
	PropertyFileUnits propertyFileUnits = PropertyFileUnits.getInstance();

	public static String getDriverClass() {

		try {
			return PropertyFileUnits.getInstance().getProperty("DRIVER_CLASS");
		} catch (PropertyFileException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getUrl() {
		try {
			return PropertyFileUnits.getInstance().getProperty("URL");
		} catch (PropertyFileException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getUserName() {
		try {
			return PropertyFileUnits.getInstance().getProperty("USER_NAME");
		} catch (PropertyFileException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getUserPassword() {
		try {
			return PropertyFileUnits.getInstance().getProperty("USER_PASSWORD");
		} catch (PropertyFileException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getTabName() {
		try {
			return PropertyFileUnits.getInstance().getProperty("TAB_NAME");
		} catch (PropertyFileException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getLogPropertyFile() {
		return LOG_PROPERTY_FILE;
	}
}