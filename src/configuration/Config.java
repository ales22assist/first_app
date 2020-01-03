package configuration;

public class Config {
	
	private static final String LOG_PROPERTY_FILE = "C:\\dev\\LOGGER\\log.properties";
	private static final String PROPERTY_FILE = "C:\\dev\\PROPERTIES\\app.properties";
	
	public static String getLogPropertyFile() {
		return LOG_PROPERTY_FILE;	
	}
	
	public static String getPropertyFile() {
		return PROPERTY_FILE;
	}
}