package de.hanneseilers.core;

/**
 * Enumuration for storing configuration values with key and default value
 * @author Hannes Eilers
 *
 */
public enum ConfigurationValues {

	APP_VERSION ("app.version", "1.0.2-alpha"),
	APP_UPDATE_REVISION ("app.update.revision", 2),
	APP_UPDATE_NAME ("app.update.name", "mybudget"),
	APP_UPDATE_URL ("app.update.url", "http://www.private-factory.de/appupdate/"),
	
	ARTICLE_TIMESTAMP_DAYS ("article.timestamp.days", 24*60*60*1000),
	ARTICLE_NAME_LENGTH ("article.name.length", 40),
	CATEGROY_NAME_LENGTH ("category.name.length", 8);
	
	
	
	
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
