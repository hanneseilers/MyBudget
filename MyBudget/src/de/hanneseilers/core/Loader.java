package de.hanneseilers.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.hanneseilers.gui.SplashScreen;

/**
 * Class for loading application
 * @author Hannes Eilers
 *
 */
public class Loader {
	
	public static PropertiesConfiguration config = null;
	private static final String configFileName = "mybudget.properties"; 
	
	private Logger logger;
	
	/**
	 * Constructor
	 */
	public Loader(){
		SplashScreen splash = new SplashScreen();
		
		// Init logger
		splash.setStatus("Loading logger...");
		initLogger();		
		logger.info("Application started.");
		
		// Load config
		splash.setStatus("Loading configuration...");
		loadConfiguration();
		
		// check for online update
		splash.setStatus("Checking for new version...");
		URL updateURL = checkForUpdate();
		if( updateURL != null ){
			
			// update available > download new version
			logger.info("Downloading update from " + updateURL);
			downloadApplication(updateURL);
			
			// restart application
			restartApplication();
			
		}
		
		// start main application
		new MyBudget(splash);
		
	}
	
	
	/**
	 * Initiates log4j logger
	 */
	private void initLogger(){		
		try{
			
			PropertyConfigurator.configureAndWatch( "log4j.properties", 60*1000 );
			logger = Logger.getLogger(getClass());
			
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * Loads config
	 */
	private void loadConfiguration(){
		
		config = new PropertiesConfiguration();
		config.setAutoSave(true);
		
		// try to load configuration
		try {				
			config.setFileName(configFileName);
			config.load();		
		} catch (ConfigurationException e) {
			try {
				// create configuration
				config.save(configFileName);				
			} catch (ConfigurationException e1) {
				logger.fatal("Can not open configuration file: " + configFileName);
			}
		}
		
		if( !checkConfig() ){
			System.out.println("init config");
			initConfig();
		}
		
	}
	
	/**
	 * Creats configuration keys inside empty config
	 */
	private void initConfig(){
		if( config != null ){
			for( ConfigurationValues configValue : ConfigurationValues.values() ){
				
				if( !config.containsKey(configValue.getKey()) ){
					config.addProperty( configValue.getKey(), configValue.getDefaultValue() );
				}
				
			}
		}
	}
	
	/**
	 * @return True if configuration contains all needed data
	 */
	private boolean checkConfig(){
		if( config != null && !config.isEmpty()){
			for( ConfigurationValues configValue : ConfigurationValues.values() ){
				if( !config.containsKey( configValue.getKey() ) )
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * @return URL of update site or null if no update
	 */
	public URL checkForUpdate(){
		URL downloadURL = null;
		String updateURL = "";
		
		try {
			
			updateURL = (String) config.getProperty( ConfigurationValues.APLLICATION_UPDATE_SYSTEM_URL.getKey() );
			URL appUpdateURL = new URL( updateURL );
			URLConnection con = appUpdateURL.openConnection();
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
			
			// TODO: Checking for update and getting download url
			System.out.println("update site: " + in.readLine());
		
		} catch (MalformedURLException e1) {
			logger.warn("Incorrect update site url " + updateURL);
		} catch (IOException e) {
			logger.warn("Can not connect to remote update site " + updateURL);
		}
		
		return downloadURL;
	}
	
	/**
	 * Downloads new program file version from url
	 * and replaced this file version
	 * @param url
	 * @return True if program successfull replaced
	 */
	public boolean downloadApplication(URL url){
		// get paths
		String pathToReplace = Loader.class.getProtectionDomain().getCodeSource().getLocation().toString();
		String pathToTemp = Loader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		// TODO: Downbloading new file and replaceing old file.
		
		return false;
	}
	
	/**
	 * Restarts application
	 */
	public void restartApplication(){
		
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File currentJar = null;
		try {
			currentJar = new File(Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		
			/* is it a jar file? */
			if(!currentJar.getName().endsWith(".jar"))
				return;
		
			/* Build command: java -jar application.jar */
			final ArrayList<String> command = new ArrayList<String>();
			command.add(javaBin);
			command.add("-jar");
			command.add(currentJar.getPath());
		
			final ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
			
		} catch (URISyntaxException e) {
			logger.warn("Error in getting path to application file.");
		} catch (IOException e) {
			logger.warn("Error while restarting application.");
		}
		
		System.exit(0);

	}

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		new Loader();
	}

}
