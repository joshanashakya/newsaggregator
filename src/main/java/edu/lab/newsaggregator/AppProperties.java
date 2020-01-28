package edu.lab.newsaggregator;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppProperties {

	private static final Logger LOGGER = Logger.getLogger(AppProperties.class.getName());
	private static AppProperties instance;
	private static Properties props = new Properties();

	static {
		instance = new AppProperties();
		try {
			props.load(instance.getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error loading properties file from application.properties.", e);
		}
	}

	private AppProperties() {
	}

	public static AppProperties getInstance() {
		return instance;
	}

	public String get(String key) {
		return (String) props.get(key);
	}
}
