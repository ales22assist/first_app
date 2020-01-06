package property_file_units;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import configuration.Config;
import exception.PropertyFileException;


public class PropertyFileUnits {

	private static final Logger LOGGER = Logger.getLogger(PropertyFileUnits.class);
	
	private static PropertyFileUnits INSTANCE;
	private final String PROPERTY_FILE = Config.getPropertyFile();
	private final Properties properties;
	
	public PropertyFileUnits() {
		LOGGER.debug("begin");
		properties = new Properties();
		
		try{
			FileInputStream fileInputStream = new FileInputStream(PROPERTY_FILE);  
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (IOException ex){
			LOGGER.error(ex);
		}
	}
	
	public static PropertyFileUnits getInstance() {
		LOGGER.debug("begin");
		if (INSTANCE == null) {
			LOGGER.debug("New instance");
			INSTANCE = new PropertyFileUnits();
		} else {
			LOGGER.debug("Old instance");
		}
		return INSTANCE;
	}
	
	public static void cancelInstance() {
		INSTANCE = null;
	}
	
	public String getProperty (String propertyName) throws PropertyFileException {
		LOGGER.debug("begin");
		
		String propertyValue = properties.getProperty(propertyName);
		LOGGER.debug("propertyName: " + propertyName);
		LOGGER.debug("propertyValue: " + propertyValue);if (propertyValue == null) {
			LOGGER.error("Property not found");
			PropertyFileException propertyFileException = new PropertyFileException("Property not Found");
			propertyFileException.setPropertyName(propertyName);
			propertyFileException.setPropertyValue(propertyValue);
			throw propertyFileException;
		}
		return propertyValue;
	
	}
}