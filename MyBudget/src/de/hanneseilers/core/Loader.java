package de.hanneseilers.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.hanneseilers.gui.SplashScreen;

/**
 * Class for loading application
 * @author Hannes Eilers
 */
public class Loader {
	
	public static PropertiesConfiguration config = null;
	private static final String configFileName = "mybudget.properties"; 
	
	public static Logger logger;
	
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
		splash.setStatus("Checking for updates...");
		List<URL> updateURLs = checkForUpdate();
		if( updateURLs.size() > 0){
			
			// update available > download new revision
			splash.setStatus("Downloading updates...");
			if( downloadApplicationUpdates(updateURLs) ){				
				logger.info("Update successfull.");
			}
			else{
				logger.error("Update failed!");				
			}
			
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
			initConfig();
		}
		
	}
	
	/**
	 * Creates default configuration values
	 * @param reset If true all default values are reseted
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
	public static List<URL> checkForUpdate(){
		List<URL> downloadURL = new ArrayList<URL>();
		String updateURL = "";
		
		try {
			
			updateURL = config.getString( ConfigurationValues.APP_UPDATE_URL.getKey() );
			updateURL += "?app=" + config.getString( ConfigurationValues.APP_UPDATE_NAME.getKey() );
			URL appUpdateURL = new URL( updateURL );
			URLConnection con = appUpdateURL.openConnection();
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
			
			// get lastest version of application
			String latestVersion = in.readLine();
			in.close();
			int appVersion = config.getInt( ConfigurationValues.APP_UPDATE_REVISION.getKey() );
			
			if( !latestVersion.equals("err")
					&& Integer.parseInt(latestVersion) > appVersion ){
				
				// get files to download
				appVersion++;
				updateURL += "&v=" + Integer.toString(appVersion);
				appUpdateURL = new URL( updateURL );
				con = appUpdateURL.openConnection();
				in = new BufferedReader( new InputStreamReader(con.getInputStream()) );
				String response = in.readLine();
				
				// extract files to download and create download urls
				if( response != null && response.trim().length() > 0  ){
					for( String url : response.trim().split(";") ){
						url = config.getString( ConfigurationValues.APP_UPDATE_URL.getKey() ) + url;
						downloadURL.add( new URL(url) );
					}
				}
				
			}
		
		} catch (MalformedURLException e1) {
			logger.warn("Incorrect update site url " + updateURL);
		} catch (IOException e) {
			logger.warn("Can not connect to remote update site " + updateURL);
		}
		
		return downloadURL;
	}
	
	/**
	 * Downloads new program files from url list
	 * @param urlList
	 * @return True if program successfull replaced
	 */
	public boolean downloadApplicationUpdates(List<URL> urlList){
		// get paths
		String pathToReplace = Loader.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		pathToReplace = pathToReplace.substring(0, pathToReplace.lastIndexOf("/")) + "/";
		pathToReplace = pathToReplace.replace("file:/", "");
		
		// fix path for linux systems
		if( System.getProperty("os.name").equals("Linux") && !pathToReplace.startsWith("/") ){
			pathToReplace = "/" + pathToReplace;
		}
		
		logger.info("Updating to revision "
				+ Integer.toString( config.getInt(ConfigurationValues.APP_UPDATE_REVISION.getKey())+1 ));
		
		/*
		 * Reset revision and app version
		 * Needs do be done before download new program file because
		 * after download no classes could be found by apacho commons configuration.
		 */
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setProperty( ConfigurationValues.APP_LAST_UPDATE.getKey(), formatter.format(new Date()) );
		config.clearProperty( ConfigurationValues.APP_UPDATE_REVISION.getKey() );
		config.clearProperty( ConfigurationValues.APP_VERSION.getKey() );
		
		// Download file
		for( URL url : urlList ){
			
			String filename = url.toExternalForm();
			filename = filename.substring( filename.lastIndexOf("/")+1 );
			
			try {

				ReadableByteChannel rbc = Channels.newChannel( url.openStream() );			
				FileOutputStream fos = new FileOutputStream( pathToReplace + filename );
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				
			} catch (IOException e) {
				logger.error("Can not replace " + pathToReplace + filename);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Restarts application
	 */
	public static boolean restartApplication(){
		
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File currentJar = null;
		try {
			currentJar = new File(Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		
			/* is it a jar file? */
			if(!currentJar.getName().endsWith(".jar"))
				return false;
		
			/* Build command: java -jar application.jar */
			final ArrayList<String> command = new ArrayList<String>();
			command.add(javaBin);
			command.add("-jar");
			command.add(currentJar.getPath());
		
			final ProcessBuilder builder = new ProcessBuilder(command);
			logger.debug("Restarting application");
			builder.start();
			
		} catch (URISyntaxException e) {
			logger.warn("Error in getting path to application file.");
		} catch (IOException e) {
			logger.warn("Error while restarting application.");
		}
		
		System.exit(0);
		return true;

	}

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		new Loader();
	}

}
