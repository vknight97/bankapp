package util;

import java.io.FileReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
	
	private static final Logger logger = LogManager.getLogger(ConfigReader.class);
	
	//instance
	private static ConfigReader instance;
	private Properties config = new Properties();
	
	final String CONFIG_FILE_PATH = "C:\\Users\\BlueA\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\bank-project\\src\\main\\resources\\config.properties";
	
	
	//private constructor
	private ConfigReader() throws Exception {
		try {
			FileReader reader = new FileReader(CONFIG_FILE_PATH); 
			logger.info("Loading configuration from " + CONFIG_FILE_PATH);
			config.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ConfigReader getInstance() throws Exception {
		if(instance == null) {
			instance = new ConfigReader();
		}
		return instance;
	}
	
	public String getProperty(String key) {
		return config.getProperty(key);
	}
}
