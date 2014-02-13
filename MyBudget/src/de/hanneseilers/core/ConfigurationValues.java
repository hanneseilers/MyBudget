package de.hanneseilers.core;

/**
 * Enumuration for storing configuration values with key and default value
 * @author Hannes Eilers
 *
 */
public enum ConfigurationValues {

	APPLICATION_VERSION ("applicationVersion", 0.1),
	APLLICATION_UPDATE_SYSTEM_URL ("apllicationUpdateSystemURL", "http://www.private-factory.de/appupdate/"),
	
	TIMESTAMP_DAYS ("timestampDays", 24*60*60*1000),
	ARTICLE_NAME_LENGTH ("articleNameLength", 40);
	
	
	
	
	private String key;
	private Object defaultValue;
	
	private ConfigurationValues(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}
	
}
